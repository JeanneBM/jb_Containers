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

