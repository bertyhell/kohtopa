use strict;
open "fortune","<FORTUNE" or die $!;

my $content = do { local $/; <fortune> };
my %fortune = map { $_=>(split $_,"\n")[0] } split "--+",$content;
while ( (my $k, my $v) = each %fortune ) {
    print "$k => $v\n";
}
print %fortune;

close "fortune";
