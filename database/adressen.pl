
use LWP::Simple;
use HTML::TableExtract;
use Data::Dumper;
#use strict;
#use warnings;

my $site = "http://www3.gent.be/immodatabank/KN/list.asp";

my $te  = new HTML::TableExtract( depth=>0, count=>4, gridmap=>0);

my $content = get($site);

$te->parse($content);

foreach $ts ($te->table_states)
{
   foreach $row ($ts->rows)
   {
      foreach(@$row) {
            print "-".$_."-";
      }
#      print Dumper $row if (scalar(@$row) == 2);
   }
}