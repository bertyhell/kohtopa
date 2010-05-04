# Script om de straten op te halen
use Data::Dumper;
use WWW::Mechanize;
use LWP::Simple;

my $mech = WWW::Mechanize->new( autocheck => 1 );

#postcodesite
my $postcodesite = "http://www.postcode.be/?q=";

#site met adressen
$site = "http://geo-vlaanderen.gisvlaanderen.be/Geo-Vlaanderen/geosjabloonnew/sjbasp/crab/crab.aspx" ;

#site ophalen
$mech->get($site);

#haal content op
my $content = $mech->content;


# hash ziet er nu uit: id=>naam
my %steden = $content =~ m|<option value="([0-9]*)">(.*)</option>|gi;

# declareer de hashes
my %output;

my %postcodes;

# overloop de steden
while ( my ($pc, $gem) = each(%steden) ) {
   #print id en gemeente
   print "$pc => $gem";
   
   #vul gemeente in in dropdownmenu
   $mech->field("ddGemeente",$pc);

	#submit het veld, opnieuw ophalen
	$content = $mech->submit()->content;
	
	#zoek straat op
	my @straat = $mech->find_all_inputs(name=>"ddStraat");
	
	#als er straten gevonden zijn: stop de straten in array
	if(@straat == 1) {
	  (undef,@straten) = $straat[0]->value_names;
	}
	
	# postcodes ophalen
	my $postcodecontent = get("$postcodesite$gem");
	if($postcodecontent =~ m/([0-9]+)(&nbsp;)+/i) {
			#indien postcode gevonden: vul in
            $postcodes{$gem} = $1;
            print " ($1)";
	} else {
			#niet gevonden: zet op 0
            $postcodes{$gem} = 0;
			#error
			print "  !=$1";
	}
	print "\n";
	
	# zet straten in de gemeenten
	$output{$gem}=\@straten;
	
	
	#print "\n@straten\n";
}

#maak uniek bestand aan om straten op te slaan
(undef,$min,$hour,undef,undef,$year,undef,$yday,undef) = localtime(time);
open BESTAND, ">straten$hour$min$yday$year" or die $!;

#vul bestand in
while ( my ($k, $v) = each(%output) ) {
  print BESTAND "$k:\n";
    foreach ( @$v ){
    print BESTAND "$_\n";
  }

}

close BESTAND;

# maak bestand aan om postcodes in op te slaan
open BESTAND,">zipcodes" or die $!;

# vul bestand in
while( my ($k,$v) = each(%postcodes)) {
	print BESTAND "$k|$v\n";
}

close BESTAND;
