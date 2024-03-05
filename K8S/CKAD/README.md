https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands#-strong-getting-started-strong-

https://kodekloud.com/topic/lab-kubernetes-challenge-2/

vim /root/.kube/config
    server: https://controlplane:6443
vim /etc/kubernetes/manifests/kube-apiserver.yaml 
    - --client-ca-file=/etc/kubernetes/pki/ca.crt
systemctl restart kubelet.service
