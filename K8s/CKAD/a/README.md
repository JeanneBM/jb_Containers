### Podstawowy pakiet najczęściej używanych komend podczas egzaminu CKAD

#### Podstawowe operacje na zasobach
```sh
kubectl get pods        # Lista podów
kubectl get nodes       # Lista nodów
kubectl get svc         # Lista usług
kubectl get deployments # Lista deploymentów
kubectl get all         # Wszystkie zasoby w namespace
kubectl describe pod <pod-name> # Szczegóły poda
kubectl delete pod <pod-name>   # Usunięcie poda
```

#### Tworzenie zasobów
```sh
kubectl run mypod --image=nginx --restart=Never # Tworzenie poda
kubectl create deployment mydep --image=nginx   # Tworzenie deploymentu
kubectl expose deployment mydep --port=80 --target-port=80 --type=NodePort  # Tworzenie usługi
kubectl apply -f config.yaml   # Tworzenie zasobów z pliku YAML
```

### Edycja i debugowanie
```sh
kubectl edit deployment mydep   # Edycja obiektu w edytorze
kubectl logs <pod-name>         # Logi poda
kubectl logs -f <pod-name>      # Stream logów
kubectl exec -it <pod-name> -- sh  # Wejście do poda
kubectl port-forward pod/<pod-name> 8080:80  # Forwardowanie portów
```

### Konfiguracja namespace
```sh
kubectl get namespaces      # Lista namespace
kubectl create namespace dev  # Tworzenie namespace
kubectl config set-context --current --namespace=dev  # Ustawienie domyślnego namespace
```

### Praca z ConfigMap i Secret
```sh
kubectl create configmap myconfig --from-literal=key1=value1  # Tworzenie ConfigMap
kubectl get configmap myconfig -o yaml  # Pobranie zawartości
kubectl create secret generic mysecret --from-literal=password=supersecret  # Tworzenie Secret
```

### Tworzenie i zarządzanie zasobami YAML
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: mypod
spec:
  containers:
    - name: nginx
      image: nginx
```
```sh
kubectl apply -f pod.yaml  # Tworzenie poda z pliku YAML
kubectl delete -f pod.yaml  # Usunięcie poda
```

### Praca z rolami i uprawnieniami (RBAC)
```sh
kubectl create role myrole --verb=get,list --resource=pods -n dev  # Tworzenie roli
kubectl create rolebinding mybinding --role=myrole --user=developer -n dev  # Przypisanie roli
```

### StatefulSet i DaemonSet
```sh
kubectl get statefulsets  # Lista StatefulSet
kubectl get daemonsets    # Lista DaemonSet
```

### HPA i Autoskalowanie
```sh
kubectl autoscale deployment mydep --cpu-percent=50 --min=1 --max=5  # Tworzenie HPA
kubectl get hpa  # Pobranie informacji o autoskalowaniu
```



