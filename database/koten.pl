# script om data van koten op te halen
use HTML::TableExtract;
use Data::Dumper;
use LWP::UserAgent;
use WWW::Mechanize;
use LWP::Simple;

#################
### kotatgent ###
#################
my $site = "http://www3.gent.be/immodatabank/KN/list.asp";

print "Visiting kotatgent...\n";

#tabel extracter aanmaken
my $te  = new HTML::TableExtract( depth=>0, count=>4, gridmap=>0);

#browser maken
my $browser = LWP::UserAgent->new();

# site bezoeken
my $m = WWW::Mechanize->new();
$m->get($site);


#vragen om 1000 koten per pagina(dan kan alles in 1x geparsed worden)
print "Requesting 1000 lines per page...\n";
my $response = HTTP::Request->new(POST => $site);
$response->content_type("application/x-www-form-urlencoded");
$response->content("LinesPerPage=1000");

#pagina ophalen
print "Fetching and parsing page...\n";
my $content = $m->request($response)->as_string;

#links ophalen
my @links = $m->find_all_links( url_regex => qr/.*eigendom=.*/i );
#print Dumper @links;

#pagina parsen
$te->parse($m->content);

#array met koten declareren, zal van vorm [ locatie, stad, type, prijs ]
my @koten;

print "Fetching prices...\n";
#tabel overlopen
foreach $ts ($te->table_states)
{
   #nutteloze rijen weg
   (undef,undef,@rijen) = $ts->rows;
   pop @rijen;
   
   #rijen overlopen
   foreach $row (@rijen)
   {
      #whitespaces weg
      foreach(@$row) {
         s/\s+$//;
         s/^\s+//;
      }
      
      #haal volgende link op
      my $subsite = shift(@links)->url_abs;
      
      #ga naar subsite
      #print "visit: $subsite";
      $content = get($subsite);
      if($content =~ m/([0-9]{3}),[0-9]{2}/) {
         #print "prijs: $1\n";
         
         push (@koten , [ $$row[3], $$row[2], $$row[4] , $1]);
      }
   }
}

print "Got prices, visiting next site...\n";

######################
### op kot in gent ###
######################

#site declareren
$site = "http://www.opkotingent.be/kot_search.php?FormProcess=OK&s_type=%25&s_order=a_adres&s_totaal=9999&s_buurt=&Submit=ZOEKEN";

print "Visiting opkotingent.be...\n";

#site ophalen
$content = get($site);


# andere tabel
print "Extracting table...\n";
$te  = new HTML::TableExtract( depth=>3);

#pagina parsen
$te->parse($content);

#tabel overlopen
print "Parsing table...\n";
foreach $ts ($te->table_states)
{
   #nutteloze rij weg
   @rijen = $ts->rows;
   
   #rijen overlopen, de whitespaces weg en opslaan
   foreach $row (@rijen)
   {
      #print Dumper @$row;
      foreach(@$row) {
         s/\s+$//;
         s/^\s+//;
      }
      
      #euro teken weg bij prijs
      $$row[1] =~ s/[^0-9]//g;
      if(@$row >= 5) {
         push (@koten , [ $$row[4], "Gent", $$row[2], $$row[1] ]);
      }
   }
}

print "Table parsed, visiting next site\n";

####################
### allekoten.be ###
####################

print "Visiting allekoten.be...\n";

#site declareren
$site = "http://www.allekoten.be/?tab=zoek&stad=gent&opp=0&huurprijs=9999&sort=&sorttype=&p=1";

# site bezoeken
$content = $m->get($site)->as_string;

#totaal pagina's: 10 zal 1 pg zijn, 11 = 2 pgs => +0.9 en afkappen
$content =~ m/van (\d*) koten./;
my $tot = int(($1/10)+0.9);

print "Visiting $tot pages\nPage 1\n";

#alle pagina's overlopen, info eruit halen
for(2..$tot) {
   @straten = $content =~ m|:</span> ?(.*?) &raquo.*</a>|gi;
   @prijzen = $content =~ m|-->.?([0-9]*).?<br />|gi;
   
   $index = 0;
   for(@straten) {
      push @koten, [ $_, "Gent", "kot", $prijzen[$index] ];
      $index++;
   }   
   
   
   #volgende pagina
   $site = "http://www.allekoten.be/?tab=zoek&stad=gent&opp=0&huurprijs=9999&sort=&sorttype=&p=$_";
   $content = $m->get($site)->as_string;
   print "Page $_\n";
}

(undef,$min,$hour,undef,undef,$year,undef,$yday,undef) = localtime(time);
open (BESTAND, ">koten$hour$min$yday$year");

foreach(@koten) {
   for(@$_) {
      print BESTAND "$_|";
   }
   print BESTAND "\n";
}
print "Job done!\n";

#printen
#print Dumper @koten;