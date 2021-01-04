I. Budowa Linuxa a Docker

Docker wykorzystuje do tworzenia kontenerów dwie cechy/funkcjonalności z jądra Linuksa. 

Pierwszą z nich jest cgroups, 
czyli wspomniany wcześniej mechanizm odpowiadający przede wszystkim za limitowanie zasobów takich jak pamięć, zużycie procesora, 
ilość operacji dyskowych czy maksymalna liczba procesów w zadanej grupie. Co więcej, mechanizm ten pozwala na zliczanie wszelkiego typu operacji.

Drugim mechanizmem są namespace’y, 
czyli przestrzenie nazw. W dużym skrócie, przestrzenie nazw odseparowują, tj. izolują procesy między różnymi przestrzeniami nazw. 
Procesom w zadanej przestrzeni nazw „wydaje się”, że są one jedynymi procesami w systemie. Przestrzenie nazw posiadają między innymi 
swoje wirtualne sieci, co jest niezbędne w przypadku kontenerów.

Manual:
man 7 namespaces i 
man 7 cgroups 

Containers are just a process (or a group of processes) running in isolation, which is achieved with Linux namespaces and control groups. 
Linux namespaces and control groups are features that are built into the Linux kernel. Other than the Linux kernel itself, there is nothing special about containers.

Kontenery to po prostu proces (lub grupa procesów) działający w izolacji, co można osiągnąć dzięki przestrzeniom nazw i grupom kontrolnym systemu Linux.
Przestrzenie nazw i grupy kontrolne systemu Linux to funkcje wbudowane w jądro systemu Linux. Poza samym jądrem Linuksa nie ma nic specjalnego w kontenerach.
#################################################################################################################################################################

II. Hypervisor

Opis działania

Ponieważ wirtualizator pozwala maksymalnej ilości procesów wirtualnego systemu operacyjnego wykonywać swoje instrukcje bezpośrednio na zasobach sprzętowych, niezbędny jest system kontroli. Jeżeli określona operacja zwraca błąd ochrony (nie daje się z jakiejś przyczyny wykonać bezpośrednio na danym zasobie sprzętowym), jest przechwytywana i emulowana przez hipernadzorcę. Hipernadzorca decyduje, które procesy wirtualizowanego systemu operacyjnego można wykonywać bezpośrednio na zasobach sprzętowych, a które należy emulować. W niektórych systemach operacyjnych hipernadzorca jest nazywany menedżerem maszyn wirtualnych (ang. virtual machine manager).

Dodatkową funkcją, jaką pełni hipernadzorca jest pośredniczenie w przekazywaniu przerwań pomiędzy wirtualnym systemem a zasobami sprzętowymi i ewentualna emulacja urządzenia po przyjęciu przerwania.

Klasyfikacja

Według Roberta P. Goldberga istnieją dwa typy hipernadzorców:

    Hipernadzorca typu 1 (tzw. natywny albo z ang. bare metal) – działa bezpośrednio na poziomie sprzętu, mając nad nim pełną kontrolę i monitorując uruchomione systemy operacyjne. Systemy operacyjne działają na poziomie wyżej niż hipernadzorca.
    Hipernadzorca typu 2 (tzw. hostowany) – działa jako program uruchomiony na danym systemie operacyjnym (hoście). Są to rodzaje emulatorów. W tym przypadku zwirtualizowane systemy działają dwa poziomy ponad sprzętem.

Przykłady hipernadzorców

Xen
Microsoft Hyper-V
Microsoft Virtual PC
VMware Workstation
VirtualBox

Emulator – program komputerowy (czasem wraz z koniecznym sprzętem), który uruchomiony w danym systemie komputerowym duplikuje funkcje innego systemu komputerowego. Pierwszy system nazywany jest gospodarzem (ang. host), a drugi gościem (ang. guest). Mówimy, że drugi system jest emulowany przez pierwszy.


https://docs.openstack.org/nova/latest/user/support-matrix.html
