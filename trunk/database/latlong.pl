use Data::Dumper;
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
              if($mess =~ s/&.*?;//g) {
                 print $mess;
              }
              chomp $sub;
              chomp $mess;
              $mess =~ s@<.*?>@@g;
              push @messages,["$sub","$mess"];                   
           } 
   }
   close "FILE";
   $/ = $tmp;
}

#print Dumper @messages;