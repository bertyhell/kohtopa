
use WWW::Mechanize;
use LWP::Simple;

my $mech = WWW::Mechanize->new( autocheck => 1 );

open FILE, "straten154657110" or die $!;

#postcodesite
my $ps = "http://www.postcode.be/?q=";

#sql commando klaarzetten
my $id = 1;

my $insert = "INSERT into addresses(addressid,street_number,street,zipcode,city,country) \nVALUES (";

open output, "> adressen.sql";
while(<FILE>) {
    chomp;
	if(m/(.*):/) {
		# postcodes ophalen
		$gem = $1;
		print $1;
		$mech->get("$ps$1");
                $c = $mech->content;
		if($c =~ m/class=txt>([0-9]*?)&/) {
		    $postcode = $1;
		    print " ($1)";
		} else {
		    $postcode = "null";
		    print " (?)";
		}
		print "\n";
	} elsif(!m/.*'.*/) {
	    $straatnr =  int(rand(10));
	    
	    for $nr (1..$straatnr) {
                if(int(rand(50))==0) {
                    $straatnr = $straatnr."A";
		    print output $insert."$id,'$nr','$_',$postcode,'$gem','Belgie');\n";
                    $id++;
                    $straatnr = $straatnr."B";
		    print output $insert."$id,'$nr','$_',$postcode,'$gem','Belgie');\n";	        
                } else {
		    print output $insert."$id,'$nr','$_',$postcode,'$gem','Belgie');\n";
                }
                $id++;
            }
	}
}
close output;
