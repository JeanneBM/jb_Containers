Podman ma kilka unikalnych komend, które nie są dostępne w Dockerze lub działają inaczej. Oto kluczowe polecenia specyficzne dla Podmana:

1. Zarządzanie Podami (Pods)
Podman obsługuje pody (grupy kontenerów współdzielących sieć i zasoby), co jest bliższe Kubernetesowi. W Dockerze tego nie ma.

* podman pod create --name mypod – Tworzy nowy pod
* podman pod ps – Wyświetla listę aktywnych podów
* podman pod rm mypod – Usuwa pod
* podman pod inspect mypod – Pobiera szczegóły o podzie

2. Tryb rootless (bez roota)
Podman działa bez roota (Docker wymaga demona z uprawnieniami roota).

* podman unshare – Tworzy nową przestrzeń użytkownika, przydatne np. do zmiany uprawnień

3. Emulacja Dockera bez demona
Podman może działać jako zamiennik Dockera bez demona:

* podman system service --time=0 – Uruchamia Podman jako usługę API kompatybilną z Dockerem
* podman --remote – Tryb zdalnego zarządzania, co w Dockerze wymaga dockerd

4. Generowanie YAML dla Kubernetes
Podman umożliwia łatwą konwersję kontenerów i podów do formatu Kubernetes YAML:

* podman generate kube mypod – Generuje plik YAML dla Kubernetes
* podman play kube mypod.yaml – Uruchamia pody z pliku YAML

5. Brak centralnego demona
Podman nie używa centralnego demona jak dockerd, więc:

* podman info – Nie zależy od działającego procesu, w przeciwieństwie do docker info
* podman ps --latest – Wybiera najnowszy kontener (bez potrzeby podawania ID)

6. Systemd i integracja z usługami
Podman obsługuje tworzenie jednostek systemd do automatycznego uruchamiania kontenerów:

* podman generate systemd --name mycontainer --new – Generuje jednostkę systemd dla kontenera

----------------------------------

Podstawowe zarządzanie kontenerami
* podman ps – Wyświetl uruchomione kontenery
* podman ps -a – Wyświetl wszystkie kontenery (w tym zatrzymane)
* podman run -it --rm alpine sh – Uruchom kontener interaktywnie i usuń go po zakończeniu
* podman start <container> – Uruchom zatrzymany kontener
* podman stop <container> – Zatrzymaj działający kontener
* podman rm <container> – Usuń kontener
* podman kill <container> – Wymuś zatrzymanie kontenera
* podman logs <container> – Wyświetl logi kontenera

Zarządzanie obrazami
* podman pull <image> – Pobierz obraz
* podman images – Wyświetl pobrane obrazy
* podman rmi <image> – Usuń obraz
* podman tag <image> <new-name> – Oznacz (przemianuj) obraz
* podman inspect <image/container> – Pobierz szczegółowe informacje

Tworzenie i uruchamianie kontenerów
* podman build -t myimage . – Zbuduj obraz z Dockerfile
* podman run -d --name mycontainer myimage – Uruchom kontener w trybie odłączonym (w tle)
* podman exec -it <container> bash – Wykonaj polecenie wewnątrz działającego kontenera

Woluminy i przechowywanie danych
* podman volume create myvol – Utwórz nazwany wolumin
* podman volume ls – Wyświetl listę woluminów
* podman volume rm myvol – Usuń wolumin
* podman run -v myvol:/data alpine ls /data – Zamontuj wolumin w kontenerze

Sieci
* podman network ls – Wyświetl listę sieci
* podman network create mynet – Utwórz nową sieć
* podman run --network mynet mycontainer – Podłącz kontener do sieci

Zarządzanie podami (styl Kubernetes)
* podman pod create --name mypod – Utwórz pod
* podman pod ps – Wyświetl listę podów
* podman pod stop mypod – Zatrzymaj pod
* podman pod rm mypod – Usuń pod

Integracja z Kubernetes
* podman generate kube <container> – Wygeneruj plik YAML Kubernetes z kontenera
* podman play kube mypod.yaml – Uruchom pod na podstawie pliku YAML Kubernetes
