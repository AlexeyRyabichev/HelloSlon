program one;

var
   name :string;
   FI, FO :text;
   c :char;
begin
     readln(name);
     Assign(FI, name + '.txt');
     Assign(FO, name);
     Reset(FI);
     Rewrite(FO);
     
     while not eof(FI) do
     begin
         read(FI, c);
         if(c = ' ') then
              writeln(FO)
         else
             write(FO, c);
     end;
     
     Close(FI);
     Close(FO);
end.