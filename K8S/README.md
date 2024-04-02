[![Python 3.8](https://img.shields.io/badge/python-3.8-blue.svg)](https://www.python.org/downloads/release/python-360/)
```
trivy k8s --report summary cluster
trivy image bitnami/nginx | grep -i critical
```

apiVersion: extensions/v1beta1 # https://kubernetes.io/blog/2019/07/18/api-deprecations-in-1-16/

https://labs.play-with-k8s.com/

https://killercoda.com/playgrounds/scenario/ckad

##
```
kubectl exec -it -n dev1401 nginx1401 -- sh
//  out with "exit"

kubectl exec -it dev-pod-dind-878516 -c log-x -- sh
-c wie container

kubectl logs dev-pod-dind-878516 -c log-x > /opt/dind-878516_logs.txt
```
```
kubectl set image deployment/nginx-deploy nginx=nginx:1.17

kubectl set serviceaccount deploy/web-dashboard dashboard-sa

kubectl set env deployment/web-dashboard zmienna="1"
```

kubectl get all --show-label

kubectl label node node01 color=blue

kubectl expose deployment my-webapp --name front-end-service --type NodePort --port 80

kubectl run messaging --image redis:alpine --labels tier=msg --dry-run=client -o yaml

```
curl 10.244.0.6
curl: (7) Failed to connect to 10.244.0.6 port 80 after 0 ms: Connection refused
```

##
https://phoenixnap.com/kb/install-kubernetes-on-ubuntu

https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/

```
error: there is no need to specify a resource type as a separate argument when passing arguments in resource/name
form (e.g. 'kubectl get resource/<resource_name>' instead of 'kubectl get resource resource/<resource_name>'
```
