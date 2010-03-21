# perl script om data van de sites te halen

use LWP::Simple;

# zet debug op 1 om te debuggen
my $DEBUG = 1;
# verbose zal code van sites tonen
my $VERB = 0;

# voornamen ophalen
my $site = "http://www.babygrafix.be/voornamen-kindernamen/";
my $page = "babynamen.html";
my $search = "[A-Z]_babynamen.html";

my $content = get("$site$page");

if(defined $content)
{
   if($VERB) { print $content; }
   
   if($DEBUG) { print "searching .*($search).* \n"; }
   
   #zoek subsites
   @subsites = $content =~ m/.*($search).*/gi;
   
   if($DEBUG) { print @subsites." found\n"; }
   
   #bezoek de subsites
   for(@subsites) {
      if($DEBUG) { print "Visiting $site$_\n"; }
      $content = get("$site$_");
      
      #haal namen op
      push(@voornamen,$content =~ m/ *([a-z]*)<br>/gi);
      
   }
}
else
{
   die "Error: website niet gevonden!";
}


# achternamen ophalen
my $site = "http://www.vroomen.be/linders/surnames-oneletter.php?firstchar=";
my $search = "[A-Z]_babynamen.html";

for(A..Z) {
   my $content = get("$site$_");
   
   if(defined $content) {
   
      if($DEBUG) { print "Visiting $site$_\n"; }
      
      #haal namen op
      push(@namen,$content =~ m/tree=1">([a-z ]*)/gi);
   }
   else {
      die "Error: website niet gevonden!";
   }
   
}

print "@voornamen";
print "@namen";



