use Data::Dumper;
use WWW::Mechanize;
use LWP::Simple;

my $mech = WWW::Mechanize->new( autocheck => 1 );

#postcodesite
my $postcodesite = "http://www.postcode.be/?q=";


$site = "http://geo-vlaanderen.gisvlaanderen.be/Geo-Vlaanderen/geosjabloonnew/sjbasp/crab/crab.aspx" ;
#site ophalen
$mech->get($site);

#print $mech->content;

my $content = $mech->content;


# hash ziet er nu uit: postcode=>naam
my %steden = $content =~ m|<option value="([0-9]*)">(.*)</option>|gi;


my %output;

my %postcodes;

while ( my ($pc, $gem) = each(%steden) ) {
   print "$pc => $gem";
   $mech->field("ddGemeente",$pc);

	#opnieuw ophalen
	$content = $mech->submit()->content;
	
	my @straat = $mech->find_all_inputs(name=>"ddStraat");
	
	if(@straat == 1) {
	  (undef,@straten) = $straat[0]->value_names;
	}
	
	# postcodes ophalen
	$postcodesite = get("$postcodesite$gem");
	if($postcodesite =~ m/class=txt>([0-9]*)&/) {
            $postcodes{$gem} = $1;
            print " ($1)";
	} else {
            $postcodes{$gem} = 0;
	}
	print "\n";
	$output{$gem}=\@straten;
	
	
	#print "\n@straten\n";
}

(undef,$min,$hour,undef,undef,$year,undef,$yday,undef) = localtime(time);
open (BESTAND, ">straten$hour$min$yday$year");

while ( my ($k, $v) = each(%output) ) {
  print BESTAND "$k:\n";
    foreach ( @$v ){
    print BESTAND "$_\n";
  }

}
