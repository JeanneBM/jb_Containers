Podstawowe zarządzanie chartami
* helm search hub <keyword> – Wyszukaj chart w Helm Hub
* helm search repo <keyword> – Wyszukaj chart w lokalnych repozytoriach
* helm show chart <chart> – Wyświetl metadane charta
* helm show values <chart> – Pokaż domyślne wartości charta

Zarządzanie repozytoriami
* helm repo list – Wyświetl listę repozytoriów Helm
* helm repo add <repo-name> <repo-url> – Dodaj repozytorium Helm
* helm repo update – Zaktualizuj listę dostępnych chartów

Instalacja i zarządzanie wydaniami
* helm install <release-name> <chart> – Zainstaluj chart jako nowe wydanie
* helm install <release-name> <chart> --values values.yaml – Zainstaluj chart z własnymi wartościami
* helm list – Wyświetl listę zainstalowanych wydań
* helm upgrade <release-name> <chart> – Zaktualizuj istniejące wydanie
* helm rollback <release-name> <revision> – Przywróć poprzednią wersję wydania
* helm uninstall <release-name> – Odinstaluj wydanie

Tworzenie i zarządzanie chartami
* helm create <chart-name> – Utwórz nowy chart
* helm package <chart-dir> – Spakuj chart do pliku .tgz
* helm lint <chart-dir> – Sprawdź poprawność charta
* helm dependency update <chart-dir> – Zaktualizuj zależności charta

Debugowanie i inspekcja
* helm get all <release-name> – Pobierz wszystkie informacje o wydaniu
* helm get values <release-name> – Pobierz wartości użyte w wydaniu
* helm get manifest <release-name> – Wyświetl manifest Kubernetes dla wydania
* helm template <chart> – Wyrenderuj manifesty YAML bez ich instalowania
* helm history <release-name> – Wyświetl historię zmian wydania
* helm diff upgrade <release-name> <chart> – Porównaj zmiany przed aktualizacją (wymaga wtyczki diff)

Integracja z Kubernetes
* helm install <release-name> <chart> --namespace <namespace> – Instalacja w określonej przestrzeni nazw
* helm upgrade --atomic <release-name> <chart> – Aktualizacja z automatycznym wycofaniem w razie błędu
* helm upgrade --wait <release-name> <chart> – Czekaj na pełne wdrożenie przed zakończeniem komendy
