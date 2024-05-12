Challenge-1 Env-Commands:
```
kubectl -n development expose pod jekyll --type NodePort --port 8080 --target-port 4000 
kubectl config set-context developer --username martin --cluster kubernetes
kubectl create rolebinding developer-rolebinding -n development --role developer-role -n development --user martin
kubectl config set-context developer --user martin --cluster kubernetes
kubectl create role developer-role --verb=get,list,watch --resource svc,pvc,pods
```
```
kubectl -n development expose pod jekyll --type NodePort --port 8080 --target-port 4000; kubectl config set-context developer --username martin --cluster kubernetes; kubectl create rolebinding developer-rolebinding -n development --role developer-role -n development --user martin; kubectl config set-context developer --user martin --cluster kubernetes; kubectl create role developer-role --verb=get,list,watch --resource svc,pvc,pods
```

Challenge-2 Env-Troubleshooting:

```
systemctl status kubelet.service
vim /root/.kube/config
    server: https://controlplane:6443
ls var/log/pods/
ls /var/log/pods/kube-system_kube-apiserver-controlplane_adb5a1efdc50603d2d81bc19442ed068/kube-apiserver
cat /var/log/pods/kube-system_kube-apiserver-controlplane_adb5a1efdc50603d2d81bc19442ed068/kube-apiserver/12.log | tail
vim /etc/kubernetes/manifests/kube-apiserver.yaml  // "ca.crt"
systemctl restart kubelet.service
kubectl get pods -A
kubectl edit pod -n kube-system coredns-5787c6fbfc-pv8xm
kubectl set image deployment/coredns -n kube-system coredns=registry.k8s.io/coredns/coredns:v1.8.6
kubectl uncordon node01
scp /media/* node01:/web
kubectl expose pod gop-file-server --name gop-fs-service --port 8080 --target-port 8080 --type NodePort
```

```
vim /root/.kube/config
    server: https://controlplane:644
vim /etc/kubernetes/manifests/kube-apiserver.yaml  // "ca.crt"
systemctl restart kubelet.service
kubectl set image deployment/coredns -n kube-system coredns=registry.k8s.io/coredns/coredns:v1.8.6; kubectl uncordon node01; scp /media/* node01:/web; kubectl expose pod gop-file-server --name gop-fs-service --port 8080 --target-port 8080 --type NodePort
```

Challenge-3 Env-Commands:

```
kubectl create ns vote
kubectl create deploy vote-deployment --image kodekloud/examplevotingapp_vote:before -n vote
kubectl -n vote expose deployment vote-deployment --name vote-service --port 5000 --target-port 80 --type NodePort
//nodeport to change via edit
kubectl expose -n vote deployment redis-deployment --name redis --port 6379 --target-port 6379
kubectl expose deployment db-deployment -n vote --name db --port 5432 --target-port 5432
kubectl create deploy worker --image kodekloud/examplevotingapp_worker -n vote
kubectl create deploy -n vote result-deployment --image kodekloud/examplevotingapp_result:before
kubectl -n vote expose deploy result-deployment --name result-service --port 5001 --target-port 80 --type NodePort

NodePorts to edit

```
```
kubectl create ns vote; kubectl create deploy vote-deployment --image kodekloud/examplevotingapp_vote:before -n vote; kubectl -n vote expose deployment vote-deployment --name vote-service --port 5000 --target-port 80 --type NodePort; kubectl expose -n vote deployment redis-deployment --name redis --port 6379 --target-port 6379; kubectl expose deployment db-deployment -n vote --name db --port 5432 --target-port 5432; kubectl create deploy worker --image kodekloud/examplevotingapp_worker -n vote; kubectl create deploy -n vote result-deployment --image kodekloud/examplevotingapp_result:before; kubectl -n vote expose deploy result-deployment --name result-service --port 5001 --target-port 80 --type NodePort
```

Challenge-4 Env-Commands:

```
kubectl apply -f sample.yaml; kubectl exec -it redis-cluster-0 -- redis-cli --cluster create --cluster-replicas 1 $(kubectl get pods -l app=redis-cluster -o jsonpath='{range.items[*]}{.status.podIP}:6379 {end}')
```
