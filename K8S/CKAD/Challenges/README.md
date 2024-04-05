Challenge-1 Env-troubleshooting:
```
kubectl -n development expose pod jekyll --type NodePort --port 8080 --target-port 4000 
kubectl config set-context developer --username martin --cluster kubernetes
kubectl create rolebinding developer-rolebinding -n development --role developer-role -n development --user martin
kubectl config set-context developer --user martin --cluster kubernetes
kubectl create role developer-role --verb=get,list,watch --resource svc,pvc,pods
```

Challenge-2 Env-troubleshooting:

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
```

Challenge-3 Env-troubleshooting:

```
kubectl create ns vote
kubectl create deploy vote-deployment --image kodekloud/examplevotingapp_vote:before -n vote
kubectl -n vote expose deployment vote-deployment --name vote-service --port 5000 --target-port 80 --type NodePort

```

Challenge-4 Env-troubleshooting:

```
```
