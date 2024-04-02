First introduction to Docker : The future of Linux Containers https://www.youtube.com/watch?v=wW9CAH9nSLs 


https://www.youtube.com/watch?v=tj3xYFA6Q2o next generation plugin

https://frontstack.pl/docker-komendy/

Budowa Linuxa a Docker

Docker wykorzystuje do tworzenia kontenerów dwie cechy Linuksa: 
```
Pierwszą z nich jest cgroups, 
czyli wspomniany wcześniej mechanizm odpowiadający przede wszystkim za limitowanie zasobów takich jak pamięć, zużycie procesora, 
ilość operacji dyskowych czy maksymalna liczba procesów w zadanej grupie. Co więcej, mechanizm ten pozwala na zliczanie wszelkiego typu operacji.

Drugim mechanizmem są namespace’y, 
czyli przestrzenie nazw. W dużym skrócie, przestrzenie nazw odseparowują, tj. izolują procesy między różnymi przestrzeniami nazw. 
Procesom w zadanej przestrzeni nazw „wydaje się”, że są one jedynymi procesami w systemie. Przestrzenie nazw posiadają między innymi 
swoje wirtualne sieci, co jest niezbędne w przypadku kontenerów.
```
