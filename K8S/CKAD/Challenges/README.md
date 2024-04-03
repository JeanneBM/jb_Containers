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
