use strict;
use DBI;
use LWP::Simple;
use Data::Dumper;

# set this to 1 for simulation
my $sim = 0;

#zipcodesite
my $zipcodesite = "http://www.postcode.be/?q=";

# files containing data
my $streetfile = "straten12568110";
my $namefile = "namen112768110";
my $rentfile = "koten93668110";
my $zipcodes = "zipcodes";



# amount of data to insert
my $nostreets = 1000;
my $nousers = 500;
my $noowners = 100;

my @letters = ('a'..'z',' ',',','.',"\n");



my $ORACLE_HOME = "C:\\oraclexe\\app\\oracle\\product\\10.2.0\\server";
my $ORACLE_SID="XE";

$ENV{ORACLE_HOME}=$ORACLE_HOME;
$ENV{ORACLE_SID}=$ORACLE_SID;
$ENV{PATH}="$ORACLE_HOME/BIN";
# $ENV{LD_LIBRARY_PATH}="$ORACLE_HOME/lib";

print "Opening file containing street data...\n";

open STREETFILE,"<$streetfile" or die $!;

print "Success!\nParsing $streetfile...\n";

my %streets;
my @city;
while(<STREETFILE>) {
   chomp;
   if(/:/){
      s/://;
      push @city,$_;
      $streets{$_} = [];
   } else {
      push @{ $streets{$city[$#city]} }, $_;
   }
}

close STREETFILE;


print "Success!\nParsing $namefile...\n";

open NAMEFILE,"<$namefile" or die $!;
my @firstnames;
my @surnames;
my $type;
while(<NAMEFILE>) {
   chomp;
   if(/::VOORNAMEN::/) {
      $type = 0;
   } elsif(/::ACHTERNAMEN::/) {
      $type = 1;
   } elsif($type) {
      push @surnames,$_;
   } else {
      push @firstnames,$_;
   }
}

print "Success!\nRetrieving zip codes...\n";


open ZIP, "<$zipcodes" or die $!;
my %zipcodes = map{ chomp; split(/\|/) } <ZIP>;
close ZIP;


print "Success!\nParsing $rentfile...\n";

open RENTFILE,"<$rentfile" or die $!;
my @rentables;
while(<RENTFILE>) {
   chomp;
   push @rentables, [split/\|/];
}
close RENTFILE;

for (@rentables) {
      #print $_->[0].": ";
   if($_->[0] =~ m/.*[:(),].*/) {
           #print "kill\n"; 
   } elsif($_->[0] =~ m/([A-Za-z]+) (\d+)\D*/) {
         $_->[0] = $1;
         $_->[4] = $2;
         #print "$1|$2\n";
   } else {
         $_->[4] = (int(rand(20)+1));
   }
   
   my $address = $_->[0]." ".$_->[4].", ".$zipcodes{$_->[1]}." ".$_->[1];
   utf8::encode($address);
    #print "$address\n";
   my $site = "http://maps.google.com/maps/api/geocode/xml?address=$address&sensor=false";
   my $content = get("$site");
   if($content =~ m@<location>((.|\n)*)</location>@) {
       my $latlng = $1;
       my $lat;
       my $lng;
       
       if($latlng =~ m|<lat>(.*)</lat>|) {
             $lat = $1;
       }        
       if($latlng =~ m|<lng>(.*)</lng>|) {
             $lng = $1;
       }
       $_->[5] = $lat;
       $_->[6] = $lng;
      print "$address ($lat,$lng)\n";
   } else {
      print "error: ".$content;
   }
   #sleep 250ms
   select(undef, undef, undef, 0.25);
}

print "Success!\nRetrieving messages...\n";
open "FILE", "<Fortunes.xml" or die $!;

my @messages;
{
   my $tmp = $/;
   $/ = "</fortune>";
   my @content = <FILE>;
   for(@content) {
           #print;
           s|<saying who="(.*?)">(.*?)</saying>|$1: $2|g;
           #print;
           if(m@.*<title>(.*?)</title>(.|\n)*<body>((.|\n)*)</body>.*@){
              my $sub = $1;
              my $mess = $3;
              chomp $sub;
              chomp $mess;
              $mess =~ s@<.*?>@@g;
              push @messages,["$sub","$mess"];                   
           } 
   }
   close "FILE";
   $/ = $tmp;
}

print "Success!\nConnecting to database...\n";

my $dbh = DBI->connect( 'dbi:Oracle:host=localhost;sid=XE',
                        'system',
                        'admin',{ RaiseError => 1, AutoCommit => 1 }
                      ) || die "Database connection not made: $DBI::errstr";

#type representation of a SQL double, used to parse string number to a valid database representation
my $doubletype = [$dbh->type_info(undef)]->[5];

print "Connection succeeded!\nExecuting insert statements for addresses...\n";     
           
my $ainsert = "INSERT INTO addresses(addressid, street_number, street, zipcode, city, country) VALUES (?, ?, ?, ?, ?, ?)";
my $asth = $dbh->prepare($ainsert) or die "insert prepare failed ($ainsert)";

my %addresses;
for(1..$nostreets) {
   my $city = $city[rand($#city)];
   my $index = $#{$streets{$city}};
   my $street = $streets{$city}[rand($index)];
   my $nr = int(rand(100))+1;
   my $zipcode = $zipcodes{$city};


   #print "INSERT INTO addresses(addressid, street_number, street, zipcode, city, country) VALUES (1,$nr,$street,$zipcode, $city, Belgie)";
   #if(defined $street && $street != '' ) {
      $addresses{$street.$nr} = [1,$nr,$street,$zipcode, $city, "BE"];
      
   #}
   
}
if($sim) {
   for my $data ( keys %addresses ) {
       print "@{ $addresses{$data} })\n";
   }   
} else {
   for my $data ( keys %addresses ) {
       $asth->execute(@{ $addresses{$data} });
   }
}

# address ids
my @addresses = @{ $dbh->selectall_arrayref("SELECT addressid from addresses") };

print "Inserted all addresses!\nExecuting insert statements for persons...\n";

   
my $pinsert = "INSERT INTO persons(personid, addressid, roleid, name, first_name, email, telephone, cellphone, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
my $psth = $dbh->prepare($pinsert) or die "insert prepare failed ($pinsert)";

my %users;
my @extensions = ("hotmail.com","telenet.be","gmail.com","skynet.be");
for(1..($nousers+$noowners)) {   
   my $addressid = ${ $addresses[int(rand($#addresses))] }[0];
   my $roleid = "user";
   
   if($_>$nousers) {
      $roleid = "owner";
   }
   
   my $name = $surnames[int(rand($#surnames))];
   my $firstname = $firstnames[int(rand($#firstnames))];   
   my $email = "$firstname.$name@".$extensions[int(rand($#extensions))];
   $email =~ s/ //g;
   my $telephone = "0";
   for(1..8) {
      $telephone .= int(rand(9));
   }
   
   my $gsm = "0";
   for(1..9) {
      $gsm .=int(rand(9));
   }
   
   my $uname = $firstname;
   my $pass = $name;
   $pass =~ s/ //g;
   #max 20 chars
   $pass =~ s/(.{20}).*/$1/;
   $uname =~ s/ //g;
   $uname =~ s/(.{20}).*/$1/;
   
   
   if(defined $addressid) {
      $users{$firstname} = [1,$addressid,$roleid,$name,$firstname,$email,$telephone,$gsm,$uname,$pass];
   }
}
   #print "$addressid, $roleid, $name, $firstname, $email, $telephone, $gsm, $uname, $pass\n";
   if($sim) {
      for my $data ( keys %users ) {
          print "@{ $users{$data} }\n";
      }
   } else {
      for my $data ( keys %users ) {
         $psth->execute(@{ $users{$data} });
      }
   }
   
   


# owner ids
my @owners = @{ $dbh->selectall_arrayref("SELECT personid from persons where roleid = 'owner'") };

# renter ids
my @renters = @{ $dbh->selectall_arrayref("SELECT personid from persons where roleid = 'user'") };

print "Inserted all persons!\nInserting...everything else :)...\n";

# commands
my $binsert = "INSERT INTO buildings(buildingid, addressid, latitude, longitude) VALUES (?, ?, ?, ?)";
my $bsth = $dbh->prepare($binsert) or die "insert prepare failed ($binsert)";

my $rinsert = "INSERT INTO rentables(rentableid, buildingid, ownerid, description, type, area, window_direction, window_area, internet, cable, outlet_count, floor, rented, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
my $rsth = $dbh->prepare($rinsert) or die "insert prepare failed ($rinsert)";

my $cinsert = "INSERT INTO contracts(contractid, rentableid, renterid, contract_start, contract_end, price, monthly_cost, guarantee) VALUES (?, ?, ?, TO_DATE(?, 'yyyy-mm-dd'), TO_DATE(?, 'yyyy-mm-dd'), ?, ?, ?)";
my $csth = $dbh->prepare($cinsert) or die "insert prepare failed ($cinsert)";

my $finsert = "INSERT INTO furniture(furnitureid, rentableid, name, price, damaged) VALUES (?, ?, ?, ?, ?)";
my $fsth = $dbh->prepare($finsert);

my $coninsert = "INSERT INTO consumption(consumptionid, rentableid, gas, water, electricity, date_consumption) VALUES (?, ?, ?, ?, ?, TO_DATE(?, 'yyyy-mm-dd'))";
my $consth = $dbh->prepare($coninsert);

my $iinsert ="INSERT INTO Invoice(invoiceid, contractid, invoicedate, paid) VALUES (?, ?, TO_DATE(?, 'yyyy-mm-dd'), ?)";
my $isth = $dbh->prepare($iinsert);

my $minsert = "INSERT INTO messages(senderid, recipientid, subject, message_read, date_sent, text) VALUES (?, ?, ?, ?, TO_DATE(?, 'yyyy-mm-dd HH24:MI:SS'),?)";
my $msth = $dbh->prepare($minsert);


my $getaddress = $dbh->prepare("SELECT addressid from addresses where street = ? and street_number = ?");
my $getbuilding = $dbh->prepare("SELECT buildingid from buildings where addressid = ?");
my $getrentable = $dbh->prepare("SELECT rentableid from rentables where buildingid = ? and description = ?");

for(@rentables) {
   
   #throw away useless info
   if(!defined $_->[4]) {
         next;
   }
   
       #$_->[5] = $lat;
       #$_->[6] = $lng;
   my $lat = $_->[5];
   my $lng = $_->[6];
   # insert address if needed   
   $getaddress->execute($_->[0],$_->[4]);
   my $aid = $getaddress->fetchrow();
   $getaddress->finish();
   
   if(!defined $aid) {
      if($sim) {
         print "1,$_->[4],$_->[0],$zipcodes{$_->[1]}, $_->[1] , 'BE'\n"
      } else {
         $asth->execute(1,$_->[4],$_->[0],$zipcodes{$_->[1]}, $_->[1] , "BE");
      }
   }
   
   # get id of address
   $getaddress->execute($_->[0],$_->[4]);
   $aid = $getaddress->fetchrow();   
   
   $getaddress->finish();
   
   # insert building if needed   
   $getbuilding->execute($aid);
   my $building = $getbuilding->fetchrow();
   $getbuilding->finish();
   
   if(!defined $building) {
      if(defined $aid) {
         if($sim) {
            print "1,$aid,undef,undef\n";
         } else {     
            #dirty solution, but oracle takes , instead of .
            $lat =~ s/\./,/;
            $lng =~ s/\./,/;
#            print $lat.", ".$lng."\n";
#            $bsth->bind_param(1,1,undef);
#            $bsth->bind_param(2,$aid,undef);
#            $bsth->bind_param(3,$lat,$doubletype);            
#            $bsth->bind_param(4,$lng,$doubletype);
            $bsth->execute(1,$aid,$lat,$lng);
         }
      }
   }
   
   # get id of building
   $getbuilding->execute($aid);
   $building = $getbuilding->fetchrow();
   $getbuilding->finish();
   
   my $area = int(($_->[3]/15)+int(rand(5)));
   my $internet = int(rand(9))>0?1:0;
   my $cable = int(rand(5))>0?1:0;
   my $outletcount = int(rand(10))+2;
   my $floor = int(rand(3));
   my $owner = $owners[int(rand($#owners))]->[0];
   my $renter = $renters[int(rand($#renters))]->[0];
   my $type = int(rand(3));
   my $desc = $_->[2].", $area m\x{00b2}, ".$_->[3]."\x{20ac}";
   if($_->[2] =~ /[sS]tudio/) {
      $type = 1;
   } elsif ($_->[2] =~ /[kK]ot/) {
      $type = rand(10)<1?1:0;
   } else {
      $type = 2;
   }
   
   
   # insert rentable
   #rentableid, buildingid, ownerid, description, type, area, window_direction, window_area, internet, cable, outlet_count, floor, rented, price)
   if(defined $owner && defined $building) {
      if($sim) {
         print "1,$building,$owner, $desc, $type, $area,undef,undef,$internet,$cable,$outletcount,$floor,undef,".$_->[3].")\n";
      } else {
         $rsth->execute(1,$building,$owner, $desc, $type, $area,undef,undef,$internet,$cable,$outletcount,$floor,undef,$_->[3]);
      }
   }
   # get id of rentable
   $getrentable->execute($building,$desc);
   my $rentable = $getrentable->fetchrow();
   $getrentable->finish();
   
   if(!defined $rentable) {
      next;
   }
   
   # insert contracts, datum van vorm yyyy-mm-dd   
   # contractid, rentableid, renterid, contract_start, contract_end, price, monthly_cost, guarantee
   my $year = 2000;
   my $end = $year+1;
   my $month = int(rand(12))+1;
   my $day = int(rand(28))+1;
   
   my $monthcost = int(rand(150))+100;
   my $guarantee = int(rand(200))+100;
   
   my $r = $renter;
   
   for my $i (1..int(rand(10))) {
         #print "1,$rentable,$r, '$year-$month-$day', '$end-$month-$day',$_->[3],$monthcost,$guarantee\n";
         if($sim) {
         } else {
            $csth->execute(1,$rentable,$r, "$year-$month-$day", "$end-$month-$day",$_->[3],$monthcost,$guarantee);
         }
      # geschiedenis simuleren
      $year= $end;
      $end++;
      
      $month++;
      if($month>12) {
         $end++;
         $year++;
         $month = 1;
      }
      
      #huurder wisselt eventueel
      if(int(rand(2))) {
         $r = $renters[int(rand($#renters))]->[0];
      }
      
      # inflatie :)
      $monthcost += int(rand(5));
      $guarantee += int(rand(5));
   }
   #print "_______________\n";
   
   
   # contract id
   my @contracts = @{ $dbh->selectall_arrayref("SELECT contractid from contracts where rentableid = $rentable and renterid = $renter") };
   
   
   # insert furniture (0-10)
   # furnitureid, rentableid, name, price, damaged
      for(0..int(rand(10))) {
         if($sim) {
            print "1,$rentable,undef,".(int(rand(50))+50).",undef\n";
         } else {
            $fsth->execute(1,$rentable,undef,int(rand(50))+50,undef);
         }
      }
   
   
   # insert consumption
   # consumptionid, rentableid, gas, water, electricity, date_consumption
   my $gas = int(rand(30000))+10000;
   my $water = int(rand(30000))+10000;
   my $el = int(rand(30000))+10000;
   my $m = $month;
   my $y = $year;
   
   # voor de makkelijkheid ervan uitgaan dat er slechts dit jaar een paar metingen gedaan zijn
   for(0..int(rand(5))) {
      $m++;
      if($m>12) {
         $y++;
         $m = 1;
      }
      
      if($sim) {
         print "1,$rentable,$gas,$water,$el,'$y-$m-$day'\n";
      } else {
         $consth->execute(1,$rentable,$gas,$water,$el,"$y-$m-$day");
      }
      $gas += int(rand(5000));
      $water +=int(rand(5000));
      $el += int(rand(5000));
   }
   
   
   
   # insert invoices
   # invoiceid, contractid, invoicedate, paid
   $m = $month;
   $y = $year;
   
   my $paid = 0;
   for(0..int(rand(7))) {
      $m++;
      if($m>12) {
         $y++;
         $m = 1;
      }
      
      # ik ga ervan uit dat paid er NIET alsvolgt uit kan zien(b=betaald,o=onbetaald):
      # obbobbobob
      # eerder iets als: bbbbbbooooooooo
      # zoniet: haal !$paid weg :)
      if(!$paid && int(rand(2))) {
         $paid = 1;
      }
      
      if(defined $contracts[0][0]) {
         if($sim) {
            print "1,$contracts[0][0],'$y-$m-$day',$paid\n";
         } else {
            $isth->execute(1,$contracts[0][0],"$y-$m-$day",$paid);
         }
      }
   }
   
   
   # insert messages
   # senderid, recipientid, subject, date_sent, text
   
   $m = $month;
   $y = $year;
   for(0..int(rand(10))) {
      
      my $entry = $messages[int(rand($#messages))];
      my $msg = $entry->[1];
      my $subject = $entry->[0];
      
      while(length($msg)>=2048 || length($subject)>=100) {
         $entry = $messages[int(rand($#messages))];
         $msg = $entry->[1];
         $subject = $entry->[0];
      }
         
      $m++;
      if($m>12) {
         $y++;
         $m = 1;
      }
	
      
      my $read = int(rand(2));
      #lazyness: one message every month :)
      #yyyy-mm-dd HH24:MI:SS
      my $hour = int(rand(24));
      
      my $min = int(rand(60));      
      my $sec = int(rand(60));
      if($hour<10) {
         $hour = "0".$hour;
      }      
      if($min<10) {
         $min = "0".$min;
      }
      if($sec<10) {
         $sec = "0".$sec;
      }
      
      # senderid, recipientid, subject, message_read, date_sent, text
      if($sim) {
         print "$owner,$renter,$subject, $read, '$y-$m-$day $hour:$min:$sec',$msg\n";
      } else {
         $msth->execute($owner,$renter,$subject, $read, "$y-$m-$day $hour:$min:$sec",$msg);
         
         $entry = $messages[int(rand($#messages))];
         $msg = $entry->[1];
         $subject = $entry->[0];
      
         while(length($msg)>=2048 || length($subject)>=100) {
            $entry = $messages[int(rand($#messages))];
            $msg = $entry->[1];
            $subject = $entry->[0];
         }   
      $m++;
      if($m>12) {
         $y++;
         $m = 1;
      }
	
      
      my $read = int(rand(2));
      #lazyness: one message every month :)
      #yyyy-mm-dd HH24:MI:SS
      my $hour = int(rand(24));
      
      my $min = int(rand(60));      
      my $sec = int(rand(60));
      if($hour<10) {
         $hour = "0".$hour;
      }      
      if($min<10) {
         $min = "0".$min;
      }
      if($sec<10) {
         $sec = "0".$sec;
      }
      
         $msth->execute($renter,$owner,$subject, $read, "$y-$m-$day $hour:$min:$sec",$msg);
      }
   }
   
   
}

print "Success!\n";

$dbh->disconnect;
print "Disconnected, have a nice day!\n";




