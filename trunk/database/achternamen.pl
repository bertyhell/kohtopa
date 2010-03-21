# perl script om data van de sites te halen

use LWP::Simple;

# zet debug op 1 om te debuggen
my $DEBUG = 1;
my $VERB = 0;

my $site = "http://www.vroomen.be/linders/surnames-oneletter.php?firstchar=";
my $search = "[A-Z]_babynamen.html";

for(A..Z) {
   my $content = get("$site$_");
   
   if($DEBUG) { print "Visiting $site$_\n"; }
   
   #haal namen op
   push(@namen,$content =~ m/tree=1">([a-z ]*)/gi);
}

print "@namen";
