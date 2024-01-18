PODs:

kubectl get pod = kubectle get pods
kubectl run nginx --image=nginx
kubectl describe pod | grep image
kubectl describe pod newpods-j7vsf
kubectl get pod webapp
kubectl describe pod webapp | grep image
kubectl describe pod webapp | grep agentx
kubectl delete pod webapp

12:
controlplane ~ ➜  kubectl run redis --image=redis123 --dry-run -o yaml
W1204 15:43:11.151261   14273 helpers.go:692] --dry-run is deprecated and can be replaced with --dry-run=client.
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: redis
  name: redis
spec:
  containers:
  - image: redis123
    name: redis
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

controlplane ~ ➜  kubectl run redis --image=redis123 --dry-run=client -o yaml
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: redis
  name: redis
spec:
  containers:
  - image: redis123
    name: redis
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

controlplane ~ ➜  kubectl run redis --image=redis123 --dry-run=client -o yaml > redis.yaml

controlplane ~ ➜  kubectl create -f redis.yaml
pod/redis created

controlplane ~ ➜  cat redis.yaml 
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: redis
  name: redis
spec:
  containers:
  - image: redis123
    name: redis
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

controlplane ~ ➜  

13:
Now change the image on this pod to redis.
Once done, the pod should be in a running state

controlplane ~ ➜  kubectl run redis --image=redis --dry-run=client -o yaml > redis.yaml

controlplane ~ ➜  cat redis.yaml 
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: redis
  name: redis
spec:
  containers:
  - image: redis
    name: redis
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

controlplane ~ ➜  kubectl create -f redis.yaml
pod/redis created

controlplane ~ ➜  

===============================================================================================================================

kubectl get replicasets

kubectl describe replicaset new-replica-set

kubectl describe pod new-replica-set-2gwrv 

11:
controlplane ~ ➜  kubectl create -f /root/replicaset-definition-1.yaml 
error: resource mapping not found for name: "replicaset-1" namespace: "" from "/root/replicaset-definition-1.yaml": no matches for kind "ReplicaSet" in version "v1"
ensure CRDs are installed first

controlplane ~ ✖ cat /root/replicaset-definition-1.yaml 
apiVersion: v1												//apiVersion: apps/v1 - correctly
kind: ReplicaSet
metadata:
  name: replicaset-1
spec:
  replicas: 2
  selector:
    matchLabels:
      tier: frontend
  template:
    metadata:
      labels:
        tier: frontend
    spec:
      containers:
      - name: nginx
        image: nginx


controlplane ~ ➜  kubectl explain replicaset
GROUP:      apps
KIND:       ReplicaSet
VERSION:    v1

DESCRIPTION:
    ReplicaSet ensures that a specified number of pod replicas are running at
    any given time.
    
FIELDS:
  apiVersion    <string>
    APIVersion defines the versioned schema of this representation of an object.
    Servers should convert recognized schemas to the latest internal value, and
    may reject unrecognized values. More info:
    https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#resources

  kind  <string>
    Kind is a string value representing the REST resource this object
    represents. Servers may infer this from the endpoint the client submits
    requests to. Cannot be updated. In CamelCase. More info:
    https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#types-kinds

  metadata      <ObjectMeta>
    If the Labels of a ReplicaSet are empty, they are defaulted to be the same
    as the Pod(s) that the ReplicaSet manages. Standard object's metadata. More
    info:
    https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata

  spec  <ReplicaSetSpec>
    Spec defines the specification of the desired behavior of the ReplicaSet.
    More info:
    https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status

  status        <ReplicaSetStatus>
    Status is the most recently observed status of the ReplicaSet. This data may
    be out of date by some window of time. Populated by the system. Read-only.
    More info:
    https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status



controlplane ~ ➜  vim /root/replicaset-definition-1.yaml 

controlplane ~ ➜  kubectl create -f /root/replicaset-definition-1.yaml 
replicaset.apps/replicaset-1 created

controlplane ~ ➜  

12:
controlplane ~ ➜  kubectl create -f /root/replicaset-definition-2.yaml 
The ReplicaSet "replicaset-2" is invalid: spec.template.metadata.labels: Invalid value: map[string]string{"tier":"nginx"}: `selector` does not match template `labels`

controlplane ~ ✖ cat replicaset-definition-2.yaml 
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: replicaset-2
spec:
  replicas: 2
  selector:
    matchLabels:
      tier: frontend
  template:
    metadata:
      labels:
        tier: nginx						//should be: "tier: frontend"
    spec:
      containers:
      - name: nginx
        image: nginx


controlplane ~ ➜  cat replicaset-definition-1.yaml 
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: replicaset-1
spec:
  replicas: 2
  selector:
    matchLabels:
      tier: frontend
  template:
    metadata:
      labels:
        tier: frontend
    spec:
      containers:
      - name: nginx
        image: nginx


controlplane ~ ➜  vim replicaset-definition-2.yaml 

controlplane ~ ➜  kubectl create -f /root/replicaset-definition-2.yaml 
replicaset.apps/replicaset-2 created

controlplane ~ ➜  

13:
controlplane ~ ➜  kubectl get replicaset // == kubectl get rs
NAME              DESIRED   CURRENT   READY   AGE
new-replica-set   4         4         0       27m
replicaset-1      2         2         2       4m51s
replicaset-2      2         2         2       103s

controlplane ~ ➜  kubectl delete replicaset replicaset-1 replicaset-2 
replicaset.apps "replicaset-1" deleted
replicaset.apps "replicaset-2" deleted

controlplane ~ ➜  

14:

controlplane ~ ➜  kubectl edit rs
replicaset.apps/new-replica-set edited

controlplane ~ ➜  kubectl get pods
NAME                    READY   STATUS             RESTARTS   AGE
new-replica-set-89c85   0/1     ImagePullBackOff   0          41m
new-replica-set-jmks6   0/1     ImagePullBackOff   0          41m
new-replica-set-tlhk2   0/1     ImagePullBackOff   0          41m
new-replica-set-46ll7   0/1     ImagePullBackOff   0          25m

controlplane ~ ➜  kubectl get rs
NAME              DESIRED   CURRENT   READY   AGE
new-replica-set   4         4         0       41m

controlplane ~ ➜  kubectl delete pod new-replica-set-*
Error from server (NotFound): pods "new-replica-set-*" not found

controlplane ~ ✖ kubectl delete pod new-replica-set-89c85 new-replica-set-jmks6 new-replica-set-tlhk2 new-replica-set-46ll7 
pod "new-replica-set-89c85" deleted
pod "new-replica-set-jmks6" deleted
pod "new-replica-set-tlhk2" deleted
pod "new-replica-set-46ll7" deleted

controlplane ~ ➜  kubectl get podsNAME                    READY   STATUS    RESTARTS   AGE
new-replica-set-sjn8s   1/1     Running   0          6s
new-replica-set-ds8xx   1/1     Running   0          6s
new-replica-set-gj9lp   1/1     Running   0          6s
new-replica-set-7rp9x   1/1     Running   0          6s

15:

controlplane ~ ✖ kubectl get pods
NAME                    READY   STATUS    RESTARTS   AGE
new-replica-set-sjn8s   1/1     Running   0          2m7s
new-replica-set-ds8xx   1/1     Running   0          2m7s
new-replica-set-gj9lp   1/1     Running   0          2m7s
new-replica-set-7rp9x   1/1     Running   0          2m7s

controlplane ~ ➜  kubectl edit rs new-replica-set 
replicaset.apps/new-replica-set edited

controlplane ~ ➜  kubectl get pods
NAME                    READY   STATUS    RESTARTS   AGE
new-replica-set-sjn8s   1/1     Running   0          2m41s
new-replica-set-ds8xx   1/1     Running   0          2m41s
new-replica-set-gj9lp   1/1     Running   0          2m41s
new-replica-set-7rp9x   1/1     Running   0          2m41s
new-replica-set-pmcls   1/1     Running   0          6s

controlplane ~ ➜  

16:

controlplane ~ ➜  kubectl get pods
NAME                    READY   STATUS    RESTARTS   AGE
new-replica-set-sjn8s   1/1     Running   0          2m41s
new-replica-set-ds8xx   1/1     Running   0          2m41s
new-replica-set-gj9lp   1/1     Running   0          2m41s
new-replica-set-7rp9x   1/1     Running   0          2m41s
new-replica-set-pmcls   1/1     Running   0          6s

controlplane ~ ➜  kubectl edit rs new-replica-set 
replicaset.apps/new-replica-set edited

controlplane ~ ➜  kubectl get pods
NAME                    READY   STATUS        RESTARTS   AGE
new-replica-set-sjn8s   1/1     Running       0          3m45s
new-replica-set-7rp9x   1/1     Running       0          3m45s
new-replica-set-pmcls   1/1     Terminating   0          70s
new-replica-set-gj9lp   1/1     Terminating   0          3m45s
new-replica-set-ds8xx   1/1     Terminating   0          3m45s

===============================================================================================================================
10:

controlplane ~ ➜  kubectl get deployments
NAME                  READY   UP-TO-DATE   AVAILABLE   AGE
frontend-deployment   0/4     4            0           2m56s

controlplane ~ ➜  ls
deployment-definition-1.yaml  sample.yaml

controlplane ~ ➜  kubectl create -f deployment-definition-1.yaml 
Error from server (BadRequest): error when creating "deployment-definition-1.yaml": deployment in version "v1" cannot be handled as a Deployment: no kind "deployment" is registered for version "apps/v1" in scheme "k8s.io/apimachinery@v1.27.1-k3s1/pkg/runtime/scheme.go:100"

controlplane ~ ✖ kubectl explain deployment
GROUP:      apps
KIND:       Deployment							
VERSION:    v1

DESCRIPTION:
    Deployment enables declarative updates for Pods and ReplicaSets.
    
FIELDS:
  apiVersion    <string>
    APIVersion defines the versioned schema of this representation of an object.
    Servers should convert recognized schemas to the latest internal value, and
    may reject unrecognized values. More info:
    https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#resources

  kind  <string>
    Kind is a string value representing the REST resource this object
    represents. Servers may infer this from the endpoint the client submits
    requests to. Cannot be updated. In CamelCase. More info:
    https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#types-kinds

  metadata      <ObjectMeta>
    Standard object's metadata. More info:
    https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata

  spec  <DeploymentSpec>
    Specification of the desired behavior of the Deployment.

  status        <DeploymentStatus>
    Most recently observed status of the Deployment.



controlplane ~ ➜  vim deployment-definition-1.yaml // kind as not with capital letter "deployment"

controlplane ~ ➜  kubectl create -f deployment-definition-1.yaml 
deployment.apps/deployment-1 created

controlplane ~ ➜  

11:


controlplane ~ ➜  ls
deployment-definition-1.yaml  sample.yaml

controlplane ~ ➜  cat deployment-definition-1.yaml 
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: deployment-1
spec:
  replicas: 2
  selector:
    matchLabels:
      name: busybox-pod
  template:
    metadata:
      labels:
        name: busybox-pod
    spec:
      containers:
      - name: busybox-container
        image: busybox888
        command:
        - sh
        - "-c"
        - echo Hello Kubernetes! && sleep 3600


controlplane ~ ➜  cp deployment-definition-1.yaml deployment-mine.yaml

controlplane ~ ➜  vim deployment-mine.yaml 

controlplane ~ ➜  kubectl create -f deployment-mine.yaml 
The Deployment "httpd-frontend" is invalid: spec.template.spec.containers[0].name: Invalid value: "httpd:2.4-alpine-container": a lowercase RFC 1123 label must consist of lower case alphanumeric characters or '-', and must start and end with an alphanumeric character (e.g. 'my-name',  or '123-abc', regex used for validation is '[a-z0-9]([-a-z0-9]*[a-z0-9])?')

controlplane ~ ✖ vim deployment-mine.yaml 

controlplane ~ ➜  kubectl create -f deployment-mine.yaml 
deployment.apps/httpd-frontend created

===============================================================================================================================
2:
controlplane ~ ➜  kubectl describe ns research
Name:         research
Labels:       kubernetes.io/metadata.name=research
Annotations:  <none>
Status:       Active

No resource quota.

No LimitRange resource.

controlplane ~ ➜  kubectl get pods --namespace=research // == kubectl get pods -n=research
NAME    READY   STATUS             RESTARTS      AGE
dna-2   0/1     CrashLoopBackOff   4 (66s ago)   2m50s
dna-1   0/1     CrashLoopBackOff   4 (64s ago)   2m50s

controlplane ~ ➜  

3:
controlplane ~ ➜  kubectl run redis --image=redis -n=finance
pod/redis created

4:

controlplane ~ ➜  kubectl get pods --all-namespaces | grep blue
marketing       blue                                     1/1     Running            0               10m

controlplane ~ ➜  

--------------------------------------------------------------------------------------------------------------------------------------------
 
controlplane ~ ➜  kubectl get ns 
NAME              STATUS   AGE
default           Active   60m
kube-system       Active   60m
kube-public       Active   60m
kube-node-lease   Active   60m
finance           Active   53m
marketing         Active   53m
dev               Active   53m
prod              Active   53m
manufacturing     Active   53m
research          Active   53m

controlplane ~ ➜  kubectl get pods -n=research
NAME    READY   STATUS             RESTARTS        AGE
dna-1   0/1     CrashLoopBackOff   15 (2m5s ago)   54m
dna-2   0/1     CrashLoopBackOff   15 (117s ago)   54m

controlplane ~ ➜  kubectl run redis --image=redis --namespace=finance
pod/redis created

controlplane ~ ➜  kubectl get pods --all-namespaces
NAMESPACE       NAME                                     READY   STATUS             RESTARTS         AGE
kube-system     local-path-provisioner-957fdf8bc-xdpg2   1/1     Running            0                62m
kube-system     coredns-77ccd57875-xfkxl                 1/1     Running            0                62m
kube-system     helm-install-traefik-crd-4n95z           0/1     Completed          0                62m
kube-system     metrics-server-54dc485875-jz5x5          1/1     Running            0                62m
kube-system     helm-install-traefik-j7bq6               0/1     Completed          1                62m
kube-system     svclb-traefik-a3299dcd-phbkn             2/2     Running            0                61m
kube-system     traefik-84745cf649-bxr84                 1/1     Running            0                61m
dev             redis-db                                 1/1     Running            0                55m
marketing       redis-db                                 1/1     Running            0                55m
manufacturing   red-app                                  1/1     Running            0                55m
marketing       blue                                     1/1     Running            0                55m
finance         payroll                                  1/1     Running            0                55m
research        dna-1                                    0/1     CrashLoopBackOff   15 (3m19s ago)   55m
research        dna-2                                    0/1     CrashLoopBackOff   15 (3m11s ago)   55m
finance         redis                                    1/1     Running            0                30s

controlplane ~ ➜  kubectl get pods --all-namespaces | grep blue
marketing       blue                                     1/1     Running            0                55m

controlplane ~ ➜  kubectl get pods -n=marketing
NAME       READY   STATUS    RESTARTS   AGE
redis-db   1/1     Running   0          56m
blue       1/1     Running   0          56m

controlplane ~ ➜  kubectl get svc -n=marketing
NAME           TYPE       CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
blue-service   NodePort   10.43.161.116   <none>        8080:30082/TCP   56m
db-service     NodePort   10.43.254.74    <none>        6379:31684/TCP   56m

controlplane ~ ➜  kubectl get svc -n=dev
NAME         TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
db-service   ClusterIP   10.43.127.225   <none>        6379/TCP   57m

controlplane ~ ➜  

===============================================================================================================================
controlplane ~ ✖ kubectl run nginx-pod --image=nginx:alpine
pod/nginx-pod created

controlplane ~ ✖ kubectl run redis --image=redis:alpine --labels="tier=db"
pod/redis created

4:
controlplane ~ ✖ kubectl expose pod redis --port=6379 --name redis-service --dry-run=client -o yaml
apiVersion: v1
kind: Service
metadata:
  creationTimestamp: null
  labels:
    tier: db
  name: redis-service
spec:
  ports:
  - port: 6379
    protocol: TCP
    targetPort: 6379
  selector:
    tier: db
status:
  loadBalancer: {}
  
controlplane ~ ✖ kubectl expose pod redis --port 6379 --name redis-service
service/redis-service exposed

5:

controlplane ~ ➜  kubectl create deployment webapp --image=kodekloud/webapp-color --replicas=3 
deployment.apps/webapp created

controlplane ~ ➜  

6:

controlplane ~ ➜  kubectl get deploy
NAME     READY   UP-TO-DATE   AVAILABLE   AGE
webapp   3/3     3            3           2m23s

controlplane ~ ➜  kubectl run custom-nginx --image=nginx --port=8080
pod/custom-nginx created

7:

controlplane ~ ➜  kubectl create ns dev-ns
namespace/dev-ns created

8:

controlplane ~ ➜  kubectl create deploy redis-deploy --namespace=dev-ns --image=redis --replicas=2
deployment.apps/redis-deploy created

9:

controlplane ~ ✖ kubectl run httpd --image=httpd:alpine --port=80 --expose=true
service/httpd created
pod/httpd created

===============================================================================================================================
3:

apiVersion: v1
kind: Pod 
metadata:
  name: ubuntu-sleeper-2
spec:
  containers:
  - name: ubuntu
    image: ubuntu
    command: [ "sleep"]
    args: ["5000"] 
	
	
controlplane ~ ➜  kubectl create -f ubuntu-sleeper-2.yaml 
pod/ubuntu-sleeper-2 created

4:

controlplane ~ ➜  kubectl create -f ubuntu-sleeper-3.yaml 
pod/ubuntu-sleeper-3 created

controlplane ~ ➜  vim ubuntu-sleeper-3.yaml 

apiVersion: v1
kind: Pod 
metadata:
  name: ubuntu-sleeper-3
spec:
  containers:
  - name: ubuntu
    image: ubuntu
    command:
    - "sleep"
    - "1200"
	
5:

controlplane ~ ➜  vim ubuntu-sleeper-3.yaml 

controlplane ~ ➜  kubectl edit pod ubuntu-sleeper-3
error: pods "ubuntu-sleeper-3" is invalid
A copy of your changes has been stored to "/tmp/kubectl-edit-1792397840.yaml"
error: Edit cancelled, no valid changes were saved.

controlplane ~ ✖ kubectl replace --force -f /tmp/kubectl-edit-1792397840.yaml
pod "ubuntu-sleeper-3" deleted
pod/ubuntu-sleeper-3 replaced

controlplane ~ ➜  

9:

controlplane ~/webapp-color-3 ➜  cat webapp-color-pod-2.yaml 
apiVersion: v1 
kind: Pod 
metadata:
  name: webapp-green
  labels:
      name: webapp-green 
spec:
  containers:
  - name: simple-webapp
    image: kodekloud/webapp-color
    command: ["python", "app.py"]
    args: ["--color", "pink"]

10:
 
controlplane ~/webapp-color-3 ➜  kubectl run webapp-green --image=kodekloud/webapp-color -- --color=green
pod/webapp-green created

controlplane ~/webapp-color-3 ➜  

===============================================================================================================================
5:

controlplane ~ ➜  kubectl get pod webapp-color -o yaml > pod.yaml 
Error from server (NotFound): pods "webapp-color" not found

controlplane ~ ✖ ls
pod.yaml     sample.yaml

controlplane ~ ➜  vim pod.yaml 

controlplane ~ ➜  cat pod.yaml 
apiVersion: v1
kind: Pod
metadata:
  labels:
    name: webapp-color
  name: webapp-color
  namespace: default
spec:
  containers:
  - env:
    - name: APP_COLOR
      value: green
    image: kodekloud/webapp-color
    name: webapp-color

controlplane ~ ➜  


controlplane ~ ➜  kubectl create -f pod.yaml 
pod/webapp-color created

controlplane ~ ➜  

ConfigMaps:

controlplane ~ ➜  kubectl get cm 
NAME               DATA   AGE
kube-root-ca.crt   1      14m
db-config          3      16s

controlplane ~ ➜  kubectl describe cm db-config 
Name:         db-config
Namespace:    default
Labels:       <none>
Annotations:  <none>

Data
====
DB_HOST:
----
SQL01.example.com
DB_NAME:
----
SQL01
DB_PORT:
----
3306

BinaryData
====

Events:  <none>

controlplane ~ ➜  


controlplane ~ ➜  kubectl create configmap  webapp-config-map --from-literal=APP_COLOR=darkblue --from-literal=APP_OTHER=disregard
configmap/webapp-config-map created

controlplane ~ ➜  kubectl get configmap
NAME                DATA   AGE
kube-root-ca.crt    1      18m
db-config           3      3m36s
webapp-config-map   2      21s

controlplane ~ ➜  kubectl get configmaps
NAME                DATA   AGE
kube-root-ca.crt    1      18m
db-config           3      3m39s
webapp-config-map   2      24s

controlplane ~ ➜  kubectl get cm
NAME                DATA   AGE
kube-root-ca.crt    1      18m
db-config           3      3m50s
webapp-config-map   2      35s

controlplane ~ ➜  

10:

controlplane ~ ➜  kubectl create -f pod2.yaml 
pod/webapp-color created

controlplane ~ ➜  vim pod2.yaml 

[1]+  Stopped                 vim pod2.yaml

controlplane ~ ✦ ✖ cat pod2.yaml 
---
apiVersion: v1
kind: Pod
metadata:
  labels:
    name: webapp-color
  name: webapp-color
  namespace: default
spec:
  containers:
  - env:
    - name: APP_COLOR
      valueFrom:
       configMapKeyRef:
         name: webapp-config-map
         key: APP_COLOR
    image: kodekloud/webapp-color
    name: webapp-color

controlplane ~ ✦ ➜  

===============================================================================================================================
controlplane ~ ➜  kubectl get secrets
NAME              TYPE                                  DATA   AGE
dashboard-token   kubernetes.io/service-account-token   3      25s

controlplane ~ ➜  kubectl describe secret dashboard-token 
Name:         dashboard-token
Namespace:    default
Labels:       <none>
Annotations:  kubernetes.io/service-account.name: dashboard-sa
              kubernetes.io/service-account.uid: 1098f14c-bc11-471b-8491-0ea782a22da1

Type:  kubernetes.io/service-account-token

Data
====
ca.crt:     566 bytes
namespace:  7 bytes
token:      eyJhbGciOiJSUzI1NiIsImtpZCI6IkU1TXl1X0xUWWZ6LW9jc0xuajBlWFVrc2k1STI4cHlPem9DVUFQeURic1EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImRhc2hib2FyZC10b2tlbiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJkYXNoYm9hcmQtc2EiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIxMDk4ZjE0Yy1iYzExLTQ3MWItODQ5MS0wZWE3ODJhMjJkYTEiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6ZGVmYXVsdDpkYXNoYm9hcmQtc2EifQ.Kz21pbjIAIp2VADoGTSJRdVn9vQifWF1-7p1LvYj4MZByIxX37U7SXBgdKxYdoL1nCE9aSW5Gt1AdqeP4I9gAVRUQgAYhYlp6Rc_YdgQ5fL2PEtRt4JtJnMLeQZtEzk6G-dS1LI9haKhxj1WrfrYmQsMfZrAs3FbFlT0KGExNdDUlzjTsFXx-esNZj7yrdQ9pYEgNhpK79vxJ4-ZkmHrOKwOSDMObyHixs9OkPpMr7cWXgUW4DtRFVSr_0byGym7cYRpUus49NrXkMXpyJR7PtZ59NVgZuyF6_GA6R3v7JlczoDJ5sdVEajgQaHiUKMFpfUyz2GRcUM12SRr4gAujQ

controlplane ~ ➜  kubectl describe secret dashboard-token 
Name:         dashboard-token
Namespace:    default
Labels:       <none>
Annotations:  kubernetes.io/service-account.name: dashboard-sa
              kubernetes.io/service-account.uid: 1098f14c-bc11-471b-8491-0ea782a22da1

Type:  kubernetes.io/service-account-token

Data
====
ca.crt:     566 bytes
namespace:  7 bytes
token:      eyJhbGciOiJSUzI1NiIsImtpZCI6IkU1TXl1X0xUWWZ6LW9jc0xuajBlWFVrc2k1STI4cHlPem9DVUFQeURic1EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImRhc2hib2FyZC10b2tlbiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJkYXNoYm9hcmQtc2EiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIxMDk4ZjE0Yy1iYzExLTQ3MWItODQ5MS0wZWE3ODJhMjJkYTEiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6ZGVmYXVsdDpkYXNoYm9hcmQtc2EifQ.Kz21pbjIAIp2VADoGTSJRdVn9vQifWF1-7p1LvYj4MZByIxX37U7SXBgdKxYdoL1nCE9aSW5Gt1AdqeP4I9gAVRUQgAYhYlp6Rc_YdgQ5fL2PEtRt4JtJnMLeQZtEzk6G-dS1LI9haKhxj1WrfrYmQsMfZrAs3FbFlT0KGExNdDUlzjTsFXx-esNZj7yrdQ9pYEgNhpK79vxJ4-ZkmHrOKwOSDMObyHixs9OkPpMr7cWXgUW4DtRFVSr_0byGym7cYRpUus49NrXkMXpyJR7PtZ59NVgZuyF6_GA6R3v7JlczoDJ5sdVEajgQaHiUKMFpfUyz2GRcUM12SRr4gAujQ

controlplane ~ ➜  kubectl create secret generic db-secret --from-literal=DB_Host=sql01 --from-literal=DB_User=root --from-literal=DB_Password=password123
secret/db-secret created

controlplane ~ ➜  

controlplane ~ ➜  kubectl create -f pod.yaml 
pod/webapp-pod created

controlplane ~ ➜  vim pod.yaml 

[1]+  Stopped                 vim pod.yaml

controlplane ~ ✦ ✖ cat pod.yaml 
---
apiVersion: v1 
kind: Pod 
metadata:
  labels:
    name: webapp-pod
  name: webapp-pod
  namespace: default 
spec:
  containers:
  - image: kodekloud/simple-webapp-mysql
    imagePullPolicy: Always
    name: webapp
    envFrom:
    - secretRef:
        name: db-secret 

controlplane ~ ✦ ➜  
===============================================================================================================================
1:

controlplane ~ ✦ ✖ kubectl exec ubuntu-sleeper -- whoami
root

controlplane ~ ✦ ➜  

2:

controlplane ~ ✦ ➜  kubectl exec ubuntu-sleeper -- chown 1010
chown: missing operand after '1010'
Try 'chown --help' for more information.
command terminated with exit code 1

controlplane ~ ✦ ✖ kubectl delete po ubuntu-sleeper 
pod "ubuntu-sleeper" deleted

controlplane ~ ✦ ➜  ls
multi-pod.yaml  sample.yaml

controlplane ~ ✦ ➜  vim pod.yaml

controlplane ~ ✦ ➜  kubectl create -f pod.yaml
pod/ubuntu-sleeper created

controlplane ~ ✦ ➜  kubectl delete po ubuntu-sleeper 
pod "ubuntu-sleeper" deleted

controlplane ~ ✦ ➜  kubectl apply -f pod.yaml 
pod/ubuntu-sleeper created

controlplane ~ ✦ ➜  cat pod.yaml 
---
apiVersion: v1
kind: Pod
metadata:
  name: ubuntu-sleeper
  namespace: default
spec:
  securityContext:
    runAsUser: 1010
  containers:
  - command:
    - sleep
    - "4800"
    image: ubuntu
    name: ubuntu-sleeper

controlplane ~ ✦ ➜ 


3:

controlplane ~ ✦ ➜  kubectl delete po ubuntu-sleeper
pod "ubuntu-sleeper" deleted

controlplane ~ ✦ ➜  vim pod2.yaml

controlplane ~ ✦ ➜  kubectl create -f pod2.yaml 
pod/ubuntu-sleeper created

controlplane ~ ✦ ➜  cat pod2.yaml 
---
apiVersion: v1
kind: Pod
metadata:
  name: ubuntu-sleeper
  namespace: default
spec:
  containers:
  - command:
    - sleep
    - "4800"
    image: ubuntu
    name: ubuntu-sleeper
    securityContext:
      capabilities:
        add: ["SYS_TIME"]

controlplane ~ ✦ ➜  

6:

controlplane ~ ✦ ➜  kubectl delete po ubuntu-sleeper
pod "ubuntu-sleeper" deleted

controlplane ~ ✦ ➜  vim pod2.yaml 

controlplane ~ ✦ ➜  kubectl create -f pod2.yaml 
pod/ubuntu-sleeper created

controlplane ~ ✦ ➜  cat pod2.yaml 
---
apiVersion: v1
kind: Pod
metadata:
  name: ubuntu-sleeper
  namespace: default
spec:
  containers:
  - command:
    - sleep
    - "4800"
    image: ubuntu
    name: ubuntu-sleeper
    securityContext:
      capabilities:
        add: ["SYS_TIME", "NET_ADMIN"]

controlplane ~ ✦ ➜  

===============================================================================================================================
controlplane ~ ➜  history
    1  kubectl describe rabbit 
    2  kubectl describe podrabbit 
    3  kubectl describe pod rabbit 
    4  kubectl describe pod rabbit | grep CPU
    5  kubectl get ns 
    6  kubectl get rabbit
    7  kubectl get pod rabbit
    8  kubectl edit pod rabbit
    9  kubectl delete pod rabbit 
   10  kubectl describe pod elephant 
   11  kubectl edit pod elephant 
   12  kubectl edit pod elephant 
   13  kubectl replace --force -f /tmp/kubectl-edit-2219967416.yaml
   14  history
   15  kubectl get pod
   16  kubectl delete pod elephant 
   17  history

controlplane ~ ➜  
===============================================================================================================================
controlplane ~ ➜  kubectl get sa
NAME      SECRETS   AGE
default   0         20m
dev       0         9m47s

controlplane ~ ➜  kubectl describe sa default
Name:                default
Namespace:           default
Labels:              <none>
Annotations:         <none>
Image pull secrets:  <none>
Mountable secrets:   <none>
Tokens:              <none>
Events:              <none>

controlplane ~ ➜  kubect get deply
-bash: kubect: command not found

controlplane ~ ✖ kubectl get deploy
NAME            READY   UP-TO-DATE   AVAILABLE   AGE
web-dashboard   1/1     1            1           35s

controlplane ~ ➜  kubectl describe deploy web-dashboard
Name:                   web-dashboard
Namespace:              default
CreationTimestamp:      Wed, 06 Dec 2023 18:48:12 +0000
Labels:                 <none>
Annotations:            deployment.kubernetes.io/revision: 1
Selector:               name=web-dashboard
Replicas:               1 desired | 1 updated | 1 total | 1 available | 0 unavailable
StrategyType:           RollingUpdate
MinReadySeconds:        0
RollingUpdateStrategy:  25% max unavailable, 25% max surge
Pod Template:
  Labels:  name=web-dashboard
  Containers:
   web-dashboard:
    Image:      gcr.io/kodekloud/customimage/my-kubernetes-dashboard
    Port:       8080/TCP
    Host Port:  0/TCP
    Environment:
      PYTHONUNBUFFERED:  1
    Mounts:              <none>
  Volumes:               <none>
Conditions:
  Type           Status  Reason
  ----           ------  ------
  Available      True    MinimumReplicasAvailable
  Progressing    True    NewReplicaSetAvailable
OldReplicaSets:  <none>
NewReplicaSet:   web-dashboard-97c9c59f6 (1/1 replicas created)
Events:
  Type    Reason             Age   From                   Message
  ----    ------             ----  ----                   -------
  Normal  ScalingReplicaSet  75s   deployment-controller  Scaled up replica set web-dashboard-97c9c59f6 to 1

controlplane ~ ➜  kubectl edit deploy web-dashboard
Edit cancelled, no changes made.

controlplane ~ ➜  kubectl get pod
NAME                            READY   STATUS    RESTARTS   AGE
web-dashboard-97c9c59f6-56nk7   1/1     Running   0          20m

controlplane ~ ➜  kubectl describe pod web-dashboard-97c9c59f6-56nk7
Name:             web-dashboard-97c9c59f6-56nk7
Namespace:        default
Priority:         0
Service Account:  default
Node:             controlplane/192.38.32.3
Start Time:       Wed, 06 Dec 2023 18:48:12 +0000
Labels:           name=web-dashboard
                  pod-template-hash=97c9c59f6
Annotations:      <none>
Status:           Running
IP:               10.42.0.9
IPs:
  IP:           10.42.0.9
Controlled By:  ReplicaSet/web-dashboard-97c9c59f6
Containers:
  web-dashboard:
    Container ID:   containerd://7d7a6f9e5a8c5685223bcf7160376fde5b1760773d17f7ecd855208ac031960a
    Image:          gcr.io/kodekloud/customimage/my-kubernetes-dashboard
    Image ID:       gcr.io/kodekloud/customimage/my-kubernetes-dashboard@sha256:7d70abe342b13ff1c4242dc83271ad73e4eedb04e2be0dd30ae7ac8852193069
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Wed, 06 Dec 2023 18:48:17 +0000
    Ready:          True
    Restart Count:  0
    Environment:
      PYTHONUNBUFFERED:  1
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-djjgw (ro)
Conditions:
  Type              Status
  Initialized       True 
  Ready             True 
  ContainersReady   True 
  PodScheduled      True 
Volumes:
  kube-api-access-djjgw:
    Type:                    Projected (a volume that contains injected data from multiple sources)
    TokenExpirationSeconds:  3607
    ConfigMapName:           kube-root-ca.crt
    ConfigMapOptional:       <nil>
    DownwardAPI:             true
QoS Class:                   BestEffort
Node-Selectors:              <none>
Tolerations:                 node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                             node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type    Reason     Age   From               Message
  ----    ------     ----  ----               -------
  Normal  Scheduled  20m   default-scheduler  Successfully assigned default/web-dashboard-97c9c59f6-56nk7 to controlplane
  Normal  Pulling    20m   kubelet            Pulling image "gcr.io/kodekloud/customimage/my-kubernetes-dashboard"
  Normal  Pulled     20m   kubelet            Successfully pulled image "gcr.io/kodekloud/customimage/my-kubernetes-dashboard" in 3.559491095s (3.559515516s including waiting)
  Normal  Created    20m   kubelet            Created container web-dashboard
  Normal  Started    20m   kubelet            Started container web-dashboard

controlplane ~ ➜  kubectl create sa -f dashboard-sa
error: unknown shorthand flag: 'f' in -f
See 'kubectl create serviceaccount --help' for usage.

controlplane ~ ✖ kubectl create sa dashboard-sa
serviceaccount/dashboard-sa created

controlplane ~ ➜  kubectl create token dashboard-sa
eyJhbGciOiJSUzI1NiIsImtpZCI6ImszaHFuYW1WN1dFSnd0bkMwZmQyVlRKVU1FblloMEZUc0JXRnBUbi1BYTQifQ.eyJhdWQiOlsiaHR0cHM6Ly9rdWJlcm5ldGVzLmRlZmF1bHQuc3ZjLmNsdXN0ZXIubG9jYWwiLCJrM3MiXSwiZXhwIjoxNzAxODkzNDgwLCJpYXQiOjE3MDE4ODk4ODAsImlzcyI6Imh0dHBzOi8va3ViZXJuZXRlcy5kZWZhdWx0LnN2Yy5jbHVzdGVyLmxvY2FsIiwia3ViZXJuZXRlcy5pbyI6eyJuYW1lc3BhY2UiOiJkZWZhdWx0Iiwic2VydmljZWFjY291bnQiOnsibmFtZSI6ImRhc2hib2FyZC1zYSIsInVpZCI6IjFmN2FlYzVmLWRkOGMtNDkwNS1hNzk1LThjN2E4OTM3MjJlNiJ9fSwibmJmIjoxNzAxODg5ODgwLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6ZGVmYXVsdDpkYXNoYm9hcmQtc2EifQ.rVE4rx6dH4SjAVQocGvqx5F67jMfY76e54HGTZmp1wzsAP30nuC_71DU3VPkowUzMxNOeAwe3_cjqiSno-1OIBoBQTa-YpkzyGysTEvN9oA2D_iHDOw-J8utolvAGgDpGYZGuZLmqLwHxnm9DKhnsWg9u3KXTjsbpkcZk2PC5CKUMEpK3ka-P8t4ABhEdHyk-vql8PnJqpirGKbo55EKpWyccW4gPlf1QpxiufUZINtuBQlCD5PvM7r_416lhIxuLN9hjyXIHSz85E_1hWjExph8W-EIQaTAB3lD5nWVn-UyD9FPR2jzhKCoApnGqqchU9pCwoap768LKu7LO_M36Q

controlplane ~ ➜  kubectl get sa
NAME           SECRETS   AGE
default        0         46m
dev            0         35m
dashboard-sa   0         2m39s

controlplane ~ ➜  kubectl get deploy
NAME            READY   UP-TO-DATE   AVAILABLE   AGE
web-dashboard   1/1     1            1           25m

controlplane ~ ➜  kubectl edit deploy web-dashboard 
deployment.apps/web-dashboard edited

controlplane ~ ➜  
===============================================================================================================================
kubectl get node
kubectl describe node

3: Create a taint on node01 with key of spray, value of mortein and effect of NoSchedule

kubectl taint nodes node01 spray=mortein:NoSchedule

4:
kubectl run mosquito --image=nginx
kubectl get pod
kubectl describe pod mosquito 
vim pod.yaml
kubectl create -f pod.yaml 

7:

controlplane ~ ➜  vim pod.yaml

controlplane ~ ➜  kubectl create -f pod.yaml 
pod/bee created

controlplane ~ ➜  kubectl get pods
NAME       READY   STATUS    RESTARTS   AGE
bee        1/1     Running   0          23s
mosquito   0/1     Pending   0          2m18s
pod        0/1     Pending   0          3m7s

controlplane ~ ➜  vim pod.yaml 

[1]+  Stopped                 vim pod.yaml

controlplane ~ ✦ ✖ cat pod.yaml
---
apiVersion: v1
kind: Pod
metadata:
  name: bee
spec:
  containers:
  - image: nginx
    name: bee
  tolerations:
  - key: spray
    value: mortein
    effect: NoSchedule
    operator: Equal
	
	
8:

Notice the bee pod was scheduled on node node01 despite the taint.

10:

kubectl taint node controlplane node-role.kubernetes.io/control-plane:NoSchedule-



===============================================================================================================================
4: Create a new deployment named blue with the nginx image and 3 replicas.

kubectl create deployment blue --image=nginx --replicas=3

5:

controlplane ~ ➜  kubectl describe node controlplane | grep -i taints
Taints:             <none>

controlplane ~ ➜  kubectl describe node node01 | grep -i taints
Taints:             <none>

controlplane ~ ➜  

6: Set Node Affinity to the deployment to place the pods on node01 only.

Name: blue
Replicas: 3
Image: nginx
NodeAffinity: requiredDuringSchedulingIgnoredDuringExecution
Key: color
value: blue


controlplane ~ ➜  kubectl delete deploy blue
deployment.apps "blue" deleted

controlplane ~ ➜  kubectl create -f pod.yaml 
deployment.apps/blue created

controlplane ~ ➜  cat pod.yaml 
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: blue
spec:
  replicas: 3
  selector:
    matchLabels:
      run: nginx
  template:
    metadata:
      labels:
        run: nginx
    spec:
      containers:
      - image: nginx
        imagePullPolicy: Always
        name: nginx
      affinity:
        nodeAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
            nodeSelectorTerms:
            - matchExpressions:
              - key: color
                operator: In
                values:
                - blue

controlplane ~ ➜  

8: Create a new deployment named red with the nginx image and 2 replicas, and ensure it gets placed on the controlplane node only.
Use the label key - node-role.kubernetes.io/control-plane - which is already set on the controlplane node.


Name: red
Replicas: 2
Image: nginx
NodeAffinity: requiredDuringSchedulingIgnoredDuringExecution
Key: node-role.kubernetes.io/control-plane


controlplane ~ ➜  vim pod2.yaml

controlplane ~ ✖ kubectl create -f pod2.yaml 
deployment.apps/red created

controlplane ~ ✖ cat pod2.yaml 
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: red
spec:
  replicas: 2
  selector:
    matchLabels:
      run: nginx
  template:
    metadata:
      labels:
        run: nginx
    spec:
      containers:
      - image: nginx
        imagePullPolicy: Always
        name: nginx
      affinity:
        nodeAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
            nodeSelectorTerms:
            - matchExpressions:
              - key: node-role.kubernetes.io/control-plane
                operator: Exists

controlplane ~ ➜  
===============================================================================================================================
3: 
Create a multi-container pod with 2 containers.
Use the spec given below:
If the pod goes into the crashloopbackoff then add the command sleep 1000 in the lemon container.

Name: yellow
Container 1 Name: lemon
Container 1 Image: busybox
Container 2 Name: gold
Container 2 Image: redis

controlplane ~ ➜  vim pod.yaml

controlplane ~ ➜  kubectl create -f pod.yaml 
pod/yellow created

controlplane ~ ➜  cat pod.yaml 
apiVersion: v1
kind: Pod
metadata:
  name: yellow
spec:
  containers:
  - name: lemon
    image: busybox
    command:
      - sleep
      - "1000"

  - name: gold
    image: redis

controlplane ~ ➜  

4,5:

kubectl -n elastic-stack logs kibana

7:

Checking the user: 
kubectl logs app -n elastic-stack


===============================================================================================================================
controlplane ~ ➜  ls
crash-app.sh  curl-test.sh  freeze-app.sh

controlplane ~ ➜  cat curl-test.sh 
for i in {1..20}; do
   kubectl exec --namespace=kube-public curl -- sh -c 'test=`wget -qO- -T 2  http://webapp-service.default.svc.cluster.local:8080/ready 2>&1` && echo "$test OK" || echo "Failed"';
   echo ""
done
controlplane ~ ➜  ./curl-test.sh 
Message from simple-webapp-1 : I am ready! OK

Message from simple-webapp-1 : I am ready! OK

Message from simple-webapp-1 : I am ready! OK

...


6:

controlplane ~ ➜  ls
crash-app.sh  curl-test.sh  freeze-app.sh

controlplane ~ ➜  vim simple-webapp-2.yaml

controlplane ~ ➜  kubectl replace -f simple-webapp-2.yaml --force
pod "simple-webapp-2" deleted
pod/simple-webapp-2 replaced

controlplane ~ ➜  cat simple-webapp-2.yaml 
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: "2021-08-01T04:55:35Z"
  labels:
    name: simple-webapp
  name: simple-webapp-2
  namespace: default
spec:
  containers:
  - env:
    - name: APP_START_DELAY
      value: "80"
    image: kodekloud/webapp-delayed-start
    imagePullPolicy: Always
    name: simple-webapp
    ports:
    - containerPort: 8080
      protocol: TCP
    readinessProbe:
      httpGet:
        path: /ready
        port: 8080

controlplane ~ ➜  

12:

controlplane ~ ➜  kubectl replace -f simple-webapp-1.yaml --force
pod "simple-webapp-1" deleted
pod/simple-webapp-1 replaced

controlplane ~ ➜  kubectl replace -f simple-webapp-2.yaml --force
pod "simple-webapp-2" deleted
pod/simple-webapp-2 replaced

controlplane ~ ➜  cat simple-webapp-1.yaml 
apiVersion: v1
kind: Pod
metadata:
  labels:
    name: simple-webapp
  name: simple-webapp-1
  namespace: default
spec:
  containers:
  - env:
    - name: APP_START_DELAY
      value: "80"
    image: kodekloud/webapp-delayed-start
    imagePullPolicy: Always
    name: simple-webapp
    ports:
    - containerPort: 8080
      protocol: TCP
    readinessProbe:
      httpGet:
        path: /ready
        port: 8080
    livenessProbe:
      httpGet:
        path: /live
        port: 8080
      periodSeconds: 1
      initialDelaySeconds: 80

controlplane ~ ➜  cat simple-webapp-2.yaml 
apiVersion: v1
kind: Pod
metadata:
  labels:
    name: simple-webapp
  name: simple-webapp-2
  namespace: default
spec:
  containers:
  - env:
    - name: APP_START_DELAY
      value: "80"
    image: kodekloud/webapp-delayed-start
    imagePullPolicy: Always
    name: simple-webapp
    ports:
    - containerPort: 8080
      protocol: TCP
    readinessProbe:
      httpGet:
        path: /ready
        port: 8080
    livenessProbe:
      httpGet:
        path: /live
        port: 8080
      periodSeconds: 1
      initialDelaySeconds: 80

controlplane ~ ➜  


===============================================================================================================================
kubectl get pod
kubectl logs webapp-1
kubectl get pod
kubectl logs webapp-2
kubectl logs webapp-2 -c simple-webapp

===============================================================================================================================
controlplane ~ ➜  kubectl get pod
NAME       READY   STATUS    RESTARTS   AGE
elephant   1/1     Running   0          10s
lion       1/1     Running   0          10s
rabbit     1/1     Running   0          10s

controlplane ~ ➜  git clone https://github.com/kodekloudhub/kubernetes-metrics-server.git
Cloning into 'kubernetes-metrics-server'...
remote: Enumerating objects: 31, done.
remote: Counting objects: 100% (19/19), done.
remote: Compressing objects: 100% (19/19), done.
remote: Total 31 (delta 8), reused 0 (delta 0), pack-reused 12
Unpacking objects: 100% (31/31), 8.06 KiB | 1.01 MiB/s, done.

controlplane ~ ➜  kubectl create -f .
error: no objects passed to create

controlplane ~ ✖ kubectl create -f .
error: no objects passed to create

controlplane ~ ✖ ls
kubernetes-metrics-server  sample.yaml

controlplane ~ ➜  cd kubernetes-metrics-server/

controlplane kubernetes-metrics-server on  master ➜  kubectl create -f .
clusterrole.rbac.authorization.k8s.io/system:aggregated-metrics-reader created
clusterrolebinding.rbac.authorization.k8s.io/metrics-server:system:auth-delegator created
rolebinding.rbac.authorization.k8s.io/metrics-server-auth-reader created
apiservice.apiregistration.k8s.io/v1beta1.metrics.k8s.io created
serviceaccount/metrics-server created
deployment.apps/metrics-server created
service/metrics-server created
clusterrole.rbac.authorization.k8s.io/system:metrics-server created
clusterrolebinding.rbac.authorization.k8s.io/system:metrics-server created

controlplane kubernetes-metrics-server on  master ➜  kubectl top node
error: metrics not available yet

controlplane kubernetes-metrics-server on  master ✖ kubectl top node
NAME           CPU(cores)   CPU%   MEMORY(bytes)   MEMORY%   
controlplane   299m         0%     1179Mi          0%        
node01         128m         0%     333Mi           0%        


controlplane kubernetes-metrics-server on  master ➜  kubectl top pod
NAME       CPU(cores)   MEMORY(bytes)   
elephant   17m          32Mi            
lion       1m           18Mi            
rabbit     113m         252Mi           

controlplane kubernetes-metrics-server on  master ➜  kubectl top pod
NAME       CPU(cores)   MEMORY(bytes)   
elephant   15m          32Mi            
lion       1m           18Mi            
rabbit     108m         252Mi           

controlplane kubernetes-metrics-server on  master ➜  
===============================================================================================================================
8:

controlplane ~ ✖ kubectl get pod red -o yaml
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: "2024-01-08T12:57:36Z"
  name: red
  namespace: default
  resourceVersion: "736"
  uid: be89aa30-4b68-4eff-a4ac-21a681c8edfa
spec:
  containers:
  - command:
    - sh
    - -c
    - echo The app is running! && sleep 3600
    image: busybox:1.28
    imagePullPolicy: IfNotPresent
    name: red-container
    resources: {}
    terminationMessagePath: /dev/termination-log
    terminationMessagePolicy: File
    volumeMounts:
    - mountPath: /var/run/secrets/kubernetes.io/serviceaccount
      name: kube-api-access-xwd4d
      readOnly: true
  dnsPolicy: ClusterFirst
  enableServiceLinks: true
  nodeName: controlplane
  preemptionPolicy: PreemptLowerPriority
  priority: 0
  restartPolicy: Always
  schedulerName: default-scheduler
  securityContext: {}
  serviceAccount: default
  serviceAccountName: default
  terminationGracePeriodSeconds: 30
  tolerations:
  - effect: NoExecute
    key: node.kubernetes.io/not-ready
    operator: Exists
    tolerationSeconds: 300
  - effect: NoExecute
    key: node.kubernetes.io/unreachable
    operator: Exists
    tolerationSeconds: 300
  volumes:
  - name: kube-api-access-xwd4d
    projected:
      defaultMode: 420
      sources:
      - serviceAccountToken:
          expirationSeconds: 3607
          path: token
      - configMap:
          items:
          - key: ca.crt
            path: ca.crt
          name: kube-root-ca.crt
      - downwardAPI:
          items:
          - fieldRef:
              apiVersion: v1
              fieldPath: metadata.namespace
            path: namespace
status:
  conditions:
  - lastProbeTime: null
    lastTransitionTime: "2024-01-08T12:57:36Z"
    status: "True"
    type: Initialized
  - lastProbeTime: null
    lastTransitionTime: "2024-01-08T12:57:38Z"
    status: "True"
    type: Ready
  - lastProbeTime: null
    lastTransitionTime: "2024-01-08T12:57:38Z"
    status: "True"
    type: ContainersReady
  - lastProbeTime: null
    lastTransitionTime: "2024-01-08T12:57:36Z"
    status: "True"
    type: PodScheduled
  containerStatuses:
  - containerID: containerd://5505ae287873047d1f1d7dd83ddd4cb4e0eb213e5b562a684715b9f8e904fa54
    image: docker.io/library/busybox:1.28
    imageID: docker.io/library/busybox@sha256:141c253bc4c3fd0a201d32dc1f493bcf3fff003b6df416dea4f41046e0f37d47
    lastState: {}
    name: red-container
    ready: true
    restartCount: 0
    started: true
    state:
      running:
        startedAt: "2024-01-08T12:57:38Z"
  hostIP: 192.29.178.3
  phase: Running
  podIP: 10.42.0.9
  podIPs:
  - ip: 10.42.0.9
  qosClass: BestEffort
  startTime: "2024-01-08T12:57:36Z"

controlplane ~ ➜  kubectl get pod red -o yaml >> red.yaml

controlplane ~ ➜  kubectl delete pod red
pod "red" deleted

controlplane ~ ➜  vim red.yaml 

controlplane ~ ➜  kubectl apply -f red.yaml
pod/red created
controlplane ~ ➜  cat red.yaml 
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: "2024-01-08T12:57:36Z"
  name: red
  namespace: default
  resourceVersion: "736"
  uid: be89aa30-4b68-4eff-a4ac-21a681c8edfa
spec:
  initContainers:
  - image: busybox
    name: red-initcontainer
    command:
        - "sleep"
        - "20"
  containers:
  - command:
    - sh
    - -c
    - echo The app is running! && sleep 3600
    image: busybox:1.28
    imagePullPolicy: IfNotPresent
    name: red-container
    resources: {}
    terminationMessagePath: /dev/termination-log
    terminationMessagePolicy: File
    volumeMounts:
    - mountPath: /var/run/secrets/kubernetes.io/serviceaccount
      name: kube-api-access-xwd4d
      readOnly: true
  dnsPolicy: ClusterFirst
  enableServiceLinks: true
  nodeName: controlplane
  preemptionPolicy: PreemptLowerPriority
  priority: 0
  restartPolicy: Always
  schedulerName: default-scheduler
  securityContext: {}
  serviceAccount: default
  serviceAccountName: default
  terminationGracePeriodSeconds: 30
  tolerations:
  - effect: NoExecute
    key: node.kubernetes.io/not-ready
    operator: Exists
    tolerationSeconds: 300
  - effect: NoExecute
    key: node.kubernetes.io/unreachable
    operator: Exists
    tolerationSeconds: 300
  volumes:
  - name: kube-api-access-xwd4d
    projected:
      defaultMode: 420
      sources:
      - serviceAccountToken:
          expirationSeconds: 3607
          path: token
      - configMap:
          items:
          - key: ca.crt
            path: ca.crt
          name: kube-root-ca.crt
      - downwardAPI:
          items:
          - fieldRef:
              apiVersion: v1
              fieldPath: metadata.namespace
            path: namespace
status:
  conditions:
  - lastProbeTime: null
    lastTransitionTime: "2024-01-08T12:57:36Z"
    status: "True"
    type: Initialized
  - lastProbeTime: null
    lastTransitionTime: "2024-01-08T12:57:38Z"
    status: "True"
    type: Ready
  - lastProbeTime: null
    lastTransitionTime: "2024-01-08T12:57:38Z"
    status: "True"
    type: ContainersReady
  - lastProbeTime: null
    lastTransitionTime: "2024-01-08T12:57:36Z"
    status: "True"
    type: PodScheduled
  containerStatuses:
  - containerID: containerd://5505ae287873047d1f1d7dd83ddd4cb4e0eb213e5b562a684715b9f8e904fa54
    image: docker.io/library/busybox:1.28
    imageID: docker.io/library/busybox@sha256:141c253bc4c3fd0a201d32dc1f493bcf3fff003b6df416dea4f41046e0f37d47
    lastState: {}
    name: red-container
    ready: true
    restartCount: 0
    started: true
    state:
      running:
        startedAt: "2024-01-08T12:57:38Z"
  hostIP: 192.29.178.3
  phase: Running
  podIP: 10.42.0.9
  podIPs:
  - ip: 10.42.0.9
  qosClass: BestEffort
  startTime: "2024-01-08T12:57:36Z"

controlplane ~ ➜  

9:




===============================================================================================================================
kubectl get pods --selector env=dev
kubectl get pods --selector env=dev --no-headers | wc -l

controlplane ~ ➜  kubectl get pods --selector bu=finance

controlplane ~ ✖ kubectl get all --selector env=prod
NAME              READY   STATUS    RESTARTS   AGE
pod/app-2-t86kl   1/1     Running   0          35m
pod/db-2-cctlt    1/1     Running   0          35m
pod/auth          1/1     Running   0          35m
pod/app-1-zzxdf   1/1     Running   0          35m

NAME            TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)    AGE
service/app-1   ClusterIP   10.43.22.173   <none>        3306/TCP   35m

NAME                    DESIRED   CURRENT   READY   AGE
replicaset.apps/app-2   1         1         1       35m
replicaset.apps/db-2    1         1         1       35m

controlplane ~ ➜  

controlplane ~ ➜  kubectl get all --selector env=prod,bu=finance,tier=frontend
NAME              READY   STATUS    RESTARTS   AGE
pod/app-1-zzxdf   1/1     Running   0          36m

controlplane ~ ➜  

controlplane ~ ➜  kubectl apply -f replicaset-definition-1.yaml 
The ReplicaSet "replicaset-1" is invalid: spec.template.metadata.labels: Invalid value: map[string]string{"tier":"nginx"}: `selector` does not match template `labels`

controlplane ~ ✖ 


=======================================================================================================================================================================
kubectl get pods --selector env=dev
kubectl get pods --selector env=dev --no-headers | wc -l
kubectl get pods --selector bu=finance
kubectl get pods --selector bu=finance --no-headers | wc -l
kubectl get all --selector env=prod
kubectl get all --selector env=prod --no-headers | wc -l
kubectl get all --selector env=prod,bu=finance,tier=frontend

replicaset-definition-1.yaml:
---
apiVersion: apps/v1
kind: ReplicaSet
metadata:
   name: replicaset-1
spec:
   replicas: 2
   selector:
      matchLabels:
        tier: front-end
   template:
     metadata:
       labels:
        tier: front-end
     spec:
       containers:
       - name: nginx
         image: nginx 

kubectl apply -f replicaset-definition-1.yaml
===============================================================================================================================
controlplane ~ ➜  history
    1  kubectl get pods 
    2  kubectl get services
    3  ./curl-test.sh 
    4  kubectl get pods
    5  kubectl get pods | wc
    6  kubectl describe pod frontend-58f7796bbb-92n2q 
    7  kubectl get deploy
    8  kubectl describe deploy
    9  kubectl describe deploy | grep strategy
   10  kubectl edit deployment frontend
   11  ./curl-test.sh 
   12  kubectl edit deployment frontend
   13  kubectl describe deploy frontend 
   14  kubectl edit deployment frontend
   15  kubectl edit deployment frontend
   16  ./curl-test.sh 
   17  history

controlplane ~ ➜  
===============================================================================================================================
controlplane ~ ✖ kubectl describe pod throw-dice-pod --image=kodekloud/throw-dice
error: unknown flag: --image
See 'kubectl describe --help' for usage.

controlplane ~ ✖ kubectl create job throw-dice-job --image=kodekloud/throw-dice --dry-run=client -o yaml  > throw-dice-job.yaml

controlplane ~ ➜  


controlplane ~ ✦ ✖ cat throw-dice-job.yaml 
apiVersion: batch/v1
https://kubernetes.io/docs/concepts/workloads/controllers/job/

kind: Job
metadata:
  creationTimestamp: null
  name: throw-dice-job
spec:
  backoffLimit: 6
  template:
    metadata:
      creationTimestamp: null
    spec:
      containers:
      - image: kodekloud/throw-dice
        name: throw-dice-job
        resources: {}
      restartPolicy: Never
status: {}

controlplane ~ ✦ ➜  history
    1  kubectl run pod throw-dice-pod.yaml 
    2  kubectl apply -f throw-dice-pod.yaml 
    3  kubectl get pod 
    4  kubect describe throw-dice-pod.yaml 
    5  kubect describe pod throw-dice-pod 
    6  kubectlt describe pod throw-dice-pod 
    7  kubectl describe pod throw-dice-pod 
    8  kubectl create job throw-dice-job --image=kodekloud/throw-dice --dry-run=client -o yaml  > throw-dice-job.yaml
    9  cat throw-dice-job.yaml 
   10  vim throw-dice-job.yaml 
   11  kubectl apply -f throw-dice-job.yaml 
   12  vim throw-dice-job.yaml 
   13  cat throw-dice-job.yaml 
   14  history

controlplane ~ ✦ ➜  

controlplane ~ ✦ ➜ kubectl logs throw-dice-job

controlplane ~ ✦ ✖ kubectl apply -f throw-cron.yaml 
cronjob.batch/throw-dice-cron-job created

controlplane ~ ✦ ➜  cat throw-cron.yaml 
apiVersion: batch/v1
kind: CronJob
metadata:
  name: throw-dice-cron-job
spec:
  schedule: "30 21 * * *"
  jobTemplate:
    spec:
      completions: 3
      parallelism: 3
      backoffLimit: 25 # This is so the job does not quit before it succeeds.
      template:
        spec:
          containers:
          - name: throw-dice
            image: kodekloud/throw-dice
          restartPolicy: Never

controlplane ~ ✦ ➜  

===============================================================================================================================
kubectl get service

---
apiVersion: v1
kind: Service
metadata:
  name: webapp-service
  namespace: default
spec:
  ports:
  - nodePort: 30080
    port: 8080
    targetPort: 8080
  selector:
    name: simple-webapp
  type: NodePort

kubectl apply -f /root/service-definition-1.yaml

===============================================================================================================================
kubectl get networkpolicy
kubectl get netpol
kubectl apply -f sample.yaml

apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: internal-policy
  namespace: default
spec:
  podSelector:
    matchLabels:
      name: internal
  policyTypes:
  - Egress
  - Ingress
  ingress:
    - {}
  egress:
  - to:
    - podSelector:
        matchLabels:
          name: mysql
    ports:
    - protocol: TCP
      port: 3306

  - to:
    - podSelector:
        matchLabels:
          name: payroll
    ports:
    - protocol: TCP
      port: 8080

  - ports:
    - port: 53
      protocol: UDP
    - port: 53
      protocol: TCP


===============================================================================================================================
kubectl get all -A
kubectl get deploy --namespace app-space
kubectl get ingress --all-namespaces
kubectl describe ingress --namespace app-space

apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    nginx.ingress.kubernetes.io/ssl-redirect: "false"
  name: ingress-wear-watch
  namespace: app-space
spec:
  rules:
  - http:
      paths:
      - backend:
          service:
            name: wear-service
            port: 
              number: 8080
        path: /wear
        pathType: Prefix
      - backend:
          service:
            name: video-service
            port: 
              number: 8080
        path: /stream
        pathType: Prefix

kubectl edit ingress --namespace app-space
kubectl get deploy --all-namespaces

---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: test-ingress
  namespace: critical-space
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    nginx.ingress.kubernetes.io/ssl-redirect: "false"
spec:
  rules:
  - http:
      paths:
      - path: /pay
        pathType: Prefix
        backend:
          service:
           name: pay-service
           port:
            number: 8282


===============================================================================================================================
controlplane ~ ➜  kubectl create serviceaccount ingress-nginx --namespace ingress-nginx; kubectl create serviceaccount ingress-nginx-admission --namespace ingress-nginx
serviceaccount/ingress-nginx created
serviceaccount/ingress-nginx-admission created

controlplane ~ ➜  

kubectl create ns ingress-nginx
kubectl create configmap ingress-nginx-controller --namespace=ingress-nginx

apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app.kubernetes.io/component: controller
    app.kubernetes.io/instance: ingress-nginx
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
    app.kubernetes.io/version: 1.1.2
    helm.sh/chart: ingress-nginx-4.0.18
  name: ingress-nginx-controller
  namespace: ingress-nginx
spec:
  minReadySeconds: 0
  revisionHistoryLimit: 10
  selector:
    matchLabels:
      app.kubernetes.io/component: controller
      app.kubernetes.io/instance: ingress-nginx
      app.kubernetes.io/name: ingress-nginx
  template:
    metadata:
      labels:
        app.kubernetes.io/component: controller
        app.kubernetes.io/instance: ingress-nginx
        app.kubernetes.io/name: ingress-nginx
    spec:
      containers:
      - args:
        - /nginx-ingress-controller
        - --publish-service=$(POD_NAMESPACE)/ingress-nginx-controller
        - --election-id=ingress-controller-leader
        - --watch-ingress-without-class=true
        - --default-backend-service=app-space/default-http-backend
        - --controller-class=k8s.io/ingress-nginx
        - --ingress-class=nginx
        - --configmap=$(POD_NAMESPACE)/ingress-nginx-controller
        - --validating-webhook=:8443
        - --validating-webhook-certificate=/usr/local/certificates/cert
        - --validating-webhook-key=/usr/local/certificates/key
        env:
        - name: POD_NAME
          valueFrom:
            fieldRef:
              fieldPath: metadata.name
        - name: POD_NAMESPACE
          valueFrom:
            fieldRef:
              fieldPath: metadata.namespace
        - name: LD_PRELOAD
          value: /usr/local/lib/libmimalloc.so
        image: registry.k8s.io/ingress-nginx/controller:v1.1.2@sha256:28b11ce69e57843de44e3db6413e98d09de0f6688e33d4bd384002a44f78405c
        imagePullPolicy: IfNotPresent
        lifecycle:
          preStop:
            exec:
              command:
              - /wait-shutdown
        livenessProbe:
          failureThreshold: 5
          httpGet:
            path: /healthz
            port: 10254
            scheme: HTTP
          initialDelaySeconds: 10
          periodSeconds: 10
          successThreshold: 1
          timeoutSeconds: 1
        name: controller
        ports:
        - name: http
          containerPort: 80
          protocol: TCP
        - containerPort: 443
          name: https
          protocol: TCP
        - containerPort: 8443
          name: webhook
          protocol: TCP
        readinessProbe:
          failureThreshold: 3
          httpGet:
            path: /healthz
            port: 10254
            scheme: HTTP
          initialDelaySeconds: 10
          periodSeconds: 10
          successThreshold: 1
          timeoutSeconds: 1
        resources:
          requests:
            cpu: 100m
            memory: 90Mi
        securityContext:
          allowPrivilegeEscalation: true
          capabilities:
            add:
            - NET_BIND_SERVICE
            drop:
            - ALL
          runAsUser: 101
        volumeMounts:
        - mountPath: /usr/local/certificates/
          name: webhook-cert
          readOnly: true
      dnsPolicy: ClusterFirst
      nodeSelector:
        kubernetes.io/os: linux
      serviceAccountName: ingress-nginx
      terminationGracePeriodSeconds: 300
      volumes:
      - name: webhook-cert
        secret:
          secretName: ingress-nginx-admission

---

apiVersion: v1
kind: Service
metadata:
  creationTimestamp: null
  labels:
    app.kubernetes.io/component: controller
    app.kubernetes.io/instance: ingress-nginx
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
    app.kubernetes.io/version: 1.1.2
    helm.sh/chart: ingress-nginx-4.0.18
  name: ingress-nginx-controller
  namespace: ingress-nginx
spec:
  ports:
  - port: 80
    protocol: TCP
    targetPort: 80
    nodePort: 30080
  selector:
    app.kubernetes.io/component: controller
    app.kubernetes.io/instance: ingress-nginx
    app.kubernetes.io/name: ingress-nginx
  type: NodePort

=================================================================================
sample.yaml:

---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: ingress-wear-watch
  namespace: app-space
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    nginx.ingress.kubernetes.io/ssl-redirect: "false"
spec:
  rules:
  - http:
      paths:
      - path: /wear
        pathType: Prefix
        backend:
          service:
           name: wear-service
           port: 
            number: 8080
      - path: /watch
        pathType: Prefix
        backend:
          service:
           name: video-service
           port:
            number: 8080

=================================================================================



===============================================================================================================================
controlplane ~ ➜  kubectl exec webapp -- cat /log/app.log
[2024-01-11 11:42:30,126] INFO in event-simulator: USER1 is viewing page1
[2024-01-11 11:42:31,127] INFO in event-simulator: USER1 is viewing page1
[2024-01-11 11:42:32,128] INFO in event-simulator: USER2 logged in
[2024-01-11 11:42:33,129] INFO in event-simulator: USER1 is viewing page3
[2024-01-11 11:42:34,130] INFO in event-simulator: USER1 logged out
[2024-01-11 11:42:35,131] WARNING in event-simulator: USER5 Failed to Login as the account is locked due to MANY FAILED ATTEMPTS.
[2024-01-11 11:42:35,131] INFO in event-simulator: USER4 is viewing page1
[2024-01-11 11:42:36,132] INFO in event-simulator: USER1 logged in
[2024-01-11 11:42:37,133] INFO in event-simulator: USER1 is viewing page3
[2024-01-11 11:42:38,134] WARNING in event-simulator: USER7 Order failed as the item is OUT OF STOCK.
[2024-01-11 11:42:38,134] INFO in event-simulator: USER4 is viewing page1
[2024-01-11 11:42:39,135] INFO in event-simulator: USER4 is viewing page1
[2024-01-11 11:42:40,136] WARNING in event-simulator: USER5 Failed to Login as the account is locked due to MANY FAILED ATTEMPTS.
[2024-01-11 11:42:40,136] INFO in event-simulator: USER1 logged out
[2024-01-11 11:42:41,137] INFO in event-simulator: USER3 is viewing page2
[2024-01-11 11:42:42,138] INFO in event-simulator: USER1 is viewing page2

controlplane ~ ➜  

kubectl get po webapp -o yaml > webapp.yaml

apiVersion: v1
kind: Pod
metadata:
  name: webapp
spec:
  containers:
  - name: event-simulator
    image: kodekloud/event-simulator
    env:
    - name: LOG_HANDLERS
      value: file
    volumeMounts:
    - mountPath: /log
      name: log-volume

  volumes:
  - name: log-volume
    hostPath:
      # directory location on host
      path: /var/log/webapp
      # this field is optional
      type: Directory

========================================================================================
sample.yaml:

apiVersion: v1
kind: Pod
metadata:
  name: webapp
spec:
  containers:
  - name: event-simulator
    image: kodekloud/event-simulator
    env:
    - name: LOG_HANDLERS
      value: file
    volumeMounts:
    - mountPath: /log
      name: log-volume

  volumes:
  - name: log-volume
    hostPath:
      # directory location on host
      path: /var/log/webapp
      # this field is optional
      type: Directory

=================================================================================================

kubectl replace -f webapp.yaml --force

controlplane ~ ➜  
=================================================================================================
controlplane ~ ➜  cat sample.yaml 
apiVersion: v1
kind: Pod
metadata:
  name: webapp
spec:
  containers:
  - name: event-simulator
    image: kodekloud/event-simulator
    env:
    - name: LOG_HANDLERS
      value: file
    volumeMounts:
    - mountPath: /log
      name: log-volume

  volumes:
  - name: log-volume
    hostPath:
      # directory location on host
      path: /var/log/webapp
      # this field is optional
      type: Directory

controlplane ~ ➜  

=================================================================================================

apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv-log
spec:
  persistentVolumeReclaimPolicy: Retain
  accessModes:
    - ReadWriteMany
  capacity:
    storage: 100Mi
  hostPath:
    path: /pv/log

=================================================================================================

kind: PersistentVolumeClaim
apiVersion: v1
metadata:
  name: claim-log-1
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 50Mi

=================================================================================================

kubectl get pvc
kubectl get pv

controlplane ~ ➜  kubectl get pvc
NAME          STATUS    VOLUME   CAPACITY   ACCESS MODES   STORAGECLASS   AGE
claim-log-1   Pending                                                     103s

controlplane ~ ➜  kubectl get pv
NAME     CAPACITY   ACCESS MODES   RECLAIM POLICY   STATUS      CLAIM   STORAGECLASS   REASON   AGE
pv-log   100Mi      RWX            Retain           Available                                   2m24s

controlplane ~ ➜  

=================================================================================================

controlplane ~ ➜  kubectl get pv,pvc
NAME                      CAPACITY   ACCESS MODES   RECLAIM POLICY   STATUS      CLAIM   STORAGECLASS   REASON   AGE
persistentvolume/pv-log   100Mi      RWX            Retain           Available                                   2m48s

NAME                                STATUS    VOLUME   CAPACITY   ACCESS MODES   STORAGECLASS   AGE
persistentvolumeclaim/claim-log-1   Pending                                                     2m9s

controlplane ~ ➜  

=================================================================================================

$ kubectl delete pvc claim-log-1

sample3.yaml:
kind: PersistentVolumeClaim
apiVersion: v1
metadata:
  name: claim-log-1
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 50Mi

=================================================================================================
$ kubectl delete po webapp

sample4.yaml:
apiVersion: v1
kind: Pod
metadata:
  name: webapp
spec:
  containers:
  - name: event-simulator
    image: kodekloud/event-simulator
    env:
    - name: LOG_HANDLERS
      value: file
    volumeMounts:
    - mountPath: /log
      name: log-volume

  volumes:
  - name: log-volume
    persistentVolumeClaim:
      claimName: claim-log-1

=================================================================================================

kubectl delete pvc claim-log-1 
kubectl delete pod webapp --force

===============================================================================================================================
kubectl get sc
kubectl describe sc portworx-io-priority-high
kubectl get pvc

---
kind: PersistentVolumeClaim
apiVersion: v1
metadata:
  name: local-pvc
spec:
  accessModes:
  - ReadWriteOnce
  storageClassName: local-storage
  resources:
    requests:
      storage: 500Mi


controlplane ~ ➜  kubectl run nginx --image=nginx:alpine --dry-run=client -oyaml > nginx.yaml

controlplane ~ ➜  cat nginx.yaml 
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: nginx
  name: nginx
spec:
  containers:
  - image: nginx:alpine
    name: nginx
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

controlplane ~ ➜  

===========================================================
---
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
    name: nginx
spec:
  containers:
  - name: nginx
    image: nginx:alpine
    volumeMounts:
      - name: local-persistent-storage
        mountPath: /var/www/html
  volumes:
    - name: local-persistent-storage
      persistentVolumeClaim:
        claimName: local-pvc

===========================================================

---
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: delayed-volume-sc
provisioner: kubernetes.io/no-provisioner
volumeBindingMode: WaitForFirstConsumer

===========================================================
===============================================================================================================================
docker images
=================================================================
cd /root/webapp-color/
docker build -t webapp-color . 

$ cat Dockerfile 
FROM python:3.6

RUN pip install flask

COPY . /opt/

EXPOSE 8080

WORKDIR /opt

ENTRYPOINT ["python", "app.py"]
$ 
=================================================================
docker run -p 8282:8080 webapp-color

Container with image 'webapp-color'
Container Port: 8080
Host Port: 8282

=================================================================

$ cat Dockerfile
FROM python:3.6-alpine

RUN pip install flask

COPY . /opt/

EXPOSE 8080

WORKDIR /opt

ENTRYPOINT ["python", "app.py"]
$ docker build -t webapp-color:lite .

=================================================================

Run an instance of the new image webapp-color:lite and publish port 8080 on the container to 8383 on the host:
docker run -p 8383:8080 webapp-color:lite



===============================================================================================================================
/root/.kube/config

controlplane ~ ➜  cat my-kube-config 
apiVersion: v1
kind: Config

clusters:
- name: production
  cluster:
    certificate-authority: /etc/kubernetes/pki/ca.crt
    server: https://controlplane:6443

- name: development
  cluster:
    certificate-authority: /etc/kubernetes/pki/ca.crt
    server: https://controlplane:6443

- name: kubernetes-on-aws
  cluster:
    certificate-authority: /etc/kubernetes/pki/ca.crt
    server: https://controlplane:6443

- name: test-cluster-1
  cluster:
    certificate-authority: /etc/kubernetes/pki/ca.crt
    server: https://controlplane:6443

contexts:
- name: test-user@development
  context:
    cluster: development
    user: test-user

- name: aws-user@kubernetes-on-aws
  context:
    cluster: kubernetes-on-aws
    user: aws-user

- name: test-user@production
  context:
    cluster: production
    user: test-user

- name: research
  context:
    cluster: test-cluster-1
    user: dev-user

users:
- name: test-user
  user:
    client-certificate: /etc/kubernetes/pki/users/test-user/test-user.crt
    client-key: /etc/kubernetes/pki/users/test-user/test-user.key
- name: dev-user
  user:
    client-certificate: /etc/kubernetes/pki/users/dev-user/developer-user.crt
    client-key: /etc/kubernetes/pki/users/dev-user/dev-user.key
- name: aws-user
  user:
    client-certificate: /etc/kubernetes/pki/users/aws-user/aws-user.crt
    client-key: /etc/kubernetes/pki/users/aws-user/aws-user.key

current-context: test-user@development
preferences: {}



To use that context, run the command: kubectl config --kubeconfig=/root/my-kube-config use-context research
To know the current context, run the command: kubectl config --kubeconfig=/root/my-kube-config current-context




===============================================================================================================================
kubectl describe pod kube-apiserver-controlplane -n kube-system
kubectl get roles
kubectl get -A roles
kubectl describe rolebinding kube-proxy -n kube-system
kubectl create role developer --namespace=default --verb=list,create,delete --resource=pods
kubectl create rolebinding dev-user-binding --namespace=default --role=developer --user=dev-user

==================================================================================================
sample.yaml:

kind: Role
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  namespace: default
  name: developer
rules:
- apiGroups: [""]
  resources: ["pods"]
  verbs: ["list", "create","delete"]

---
kind: RoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: dev-user-binding
subjects:
- kind: User
  name: dev-user
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role
  name: developer
  apiGroup: rbac.authorization.k8s.io

==================================================================================================

kubectl edit role developer -n blue

sample1.yaml:
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: developer
  namespace: blue
rules:
- apiGroups:
  - apps
  resourceNames:
  - dark-blue-app
  resources:
  - pods
  verbs:
  - get
  - watch
  - create
  - delete
- apiGroups:
  - apps
  resources:
  - deployments
  verbs:
  - create

==================================================================================================
===============================================================================================================================
kubectl get clusterroles | wc
kubectl get clusterrolebindings | wc
kubectl describe clusterrolebinding cluster-admin

=================================================================
sample.yaml
---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: node-admin
rules:
- apiGroups: [""]
  resources: ["nodes"]
  verbs: ["get", "watch", "list", "create", "delete"]

---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: michelle-binding
subjects:
- kind: User
  name: michelle
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: node-admin
  apiGroup: rbac.authorization.k8s.io

=================================================================
sample1.yaml:
---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: storage-admin
rules:
- apiGroups: [""]
  resources: ["persistentvolumes"]
  verbs: ["get", "watch", "list", "create", "delete"]
- apiGroups: ["storage.k8s.io"]
  resources: ["storageclasses"]
  verbs: ["get", "watch", "list", "create", "delete"]

---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: michelle-storage-admin
subjects:
- kind: User
  name: michelle
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: storage-admin
  apiGroup: rbac.authorization.k8s.io

=================================================================
===============================================================================================================================
kubectl exec -it kube-apiserver-controlplane -n kube-system -- kube-apiserver -h | grep 'enable-admission-plugins'
/etc/kubernetes/manifests/kube-apiserver.yaml

kubectl run nginx --image nginx -n blue

Edit /etc/kubernetes/manifests/kube-apiserver.yaml and add NamespaceAutoProvision admission controller to --enable-admission-plugins list
Note: Once you update kube-apiserver yaml file then please wait few mins for the kube-apiserver to restart completely.

kubectl run nginx --image nginx -n blue

--disable-admission-plugins=DefaultStorageClass

ps -ef | grep kube-apiserver | grep admission-plugins

===============================================================================================================================
kubectl create ns webhook-demo
kubectl -n webhook-demo create secret tls webhook-server-tls \
    --cert "/root/keys/webhook-server-tls.crt" \
    --key "/root/keys/webhook-server-tls.key"

controlplane ~ ➜  kubectl create -f /root/webhook-deployment.yaml
deployment.apps/webhook-server created

controlplane ~ ➜  cat /root/webhook-deployment.yaml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: webhook-server
  namespace: webhook-demo
  labels:
    app: webhook-server
spec:
  replicas: 1
  selector:
    matchLabels:
      app: webhook-server
  template:
    metadata:
      labels:
        app: webhook-server
    spec:
      securityContext:
        runAsNonRoot: true
        runAsUser: 1234
      containers:
      - name: server
        image: stackrox/admission-controller-webhook-demo:latest
        imagePullPolicy: Always
        ports:
        - containerPort: 8443
          name: webhook-api
        volumeMounts:
        - name: webhook-tls-certs
          mountPath: /run/secrets/tls
          readOnly: true
      volumes:
      - name: webhook-tls-certs
        secret:
          secretName: webhook-server-tls


controlplane ~ ➜  kubectl apply -f /root/webhook-service.yaml
service/webhook-server created

controlplane ~ ➜  cat /root/webhook-service.yaml
apiVersion: v1
kind: Service
metadata:
  name: webhook-server
  namespace: webhook-demo
spec:
  selector:
    app: webhook-server
  ports:
    - port: 443
      targetPort: webhook-api

controlplane ~ ➜  

controlplane ~ ➜  cat /root/webhook-configuration.yaml
apiVersion: admissionregistration.k8s.io/v1
kind: MutatingWebhookConfiguration
metadata:
  name: demo-webhook
webhooks:
  - name: webhook-server.webhook-demo.svc
    clientConfig:
      service:
        name: webhook-server
        namespace: webhook-demo
        path: "/mutate"
      caBundle: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSURQekNDQWllZ0F3SUJBZ0lVQ3hxaUdrcnlGakc5VU5DL3ZqSU5Odnora2Z3d0RRWUpLb1pJaHZjTkFRRUwKQlFBd0x6RXRNQ3NHQTFVRUF3d2tRV1J0YVhOemFXOXVJRU52Ym5SeWIyeHNaWElnVjJWaWFHOXZheUJFWlcxdgpJRU5CTUI0WERUSTBNREV4TnpJd05EZ3hORm9YRFRJME1ESXhOakl3TkRneE5Gb3dMekV0TUNzR0ExVUVBd3drClFXUnRhWE56YVc5dUlFTnZiblJ5YjJ4c1pYSWdWMlZpYUc5dmF5QkVaVzF2SUVOQk1JSUJJakFOQmdrcWhraUcKOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDQVFFQTRybWZGRlhBSEY1cGRqcjBTNzR4UFBCY0t1SmN5ZXN2L1ZpTQpMaS9LQitheFo2QzJtdU16S2h6SmpuNzZUbW9icVA1Z29xWUtsTW8wSzBBS0EzUUdqZml3bUs3ejErZkVXdnBvCkpjL0xLemtJTUJqdWRtckNhSG02Q0N2dzl2dGExWXRGQUN5RXdQcmc3MUxjWlJHTXNFRC94WHlSVXdrYmFvU3YKOHJjV0pNUEFjRCt5SUdVc3BnbEhBeFhhOHA3bWM1NDVMR0FpeVB4bW9VbWhuS2Y0bUFlNFhZMjZvMk5lVlhXQgpMR25NVFJVMk9saVRPd1RoNERsRmozbkNMVHY5ZWxNVE05RGNLMldVYUtORlRJb0NMSXNmMUFZK0tYK2dVNCsrCnIzRmN5VWN2Q2JoaUl4ejU4bEljeTN0aHRpcWZTOHEvdStqUHJMNG5MNkt3dVNaQjZ3SURBUUFCbzFNd1VUQWQKQmdOVkhRNEVGZ1FVTlR3a0d1ZDJGR1ViUHNUeWVoSTNnbG1QWTFnd0h3WURWUjBqQkJnd0ZvQVVOVHdrR3VkMgpGR1ViUHNUeWVoSTNnbG1QWTFnd0R3WURWUjBUQVFIL0JBVXdBd0VCL3pBTkJna3Foa2lHOXcwQkFRc0ZBQU9DCkFRRUFzSWZnaFIwd2I3Qk5DRGxrZ2hERSsxbjhQNld2QURLL0cyMmxqOENYcVJWelBXeVh0cnJzRm9vbGZvMlAKbFBkSDRkZXlGSnlaM285M3RNc01sZ1FXdVR6UWY2UHVyS3JoVEFtUk9RWjlXSTh5SnFrd0h5cWZ5Z09veFgxcwo3cFQwQms0OUlBMElENHBQamRpcU51ck9ITDkxSDR5TDZtTllNSjNBRGtKOUhURlFYanUrOFR3bmcvczc1eC85CmZtUjRZTU5qb3RIY0IyOFlNVE84cWhvc0lydVA3WGo2OHF5bU80aDZwR1pIT3BYZ1JVcXlNK2hBekdPSDRibWkKdnNudng1ZGJjUTZBdkQxeWtpQXM0L0Zjb3Q1Q3A5UHpVa1d4RlVBK2JnRUR2dHFUZGx3TkRXTFY1Q2hSNENISgplWjRwZnNXeWExOU8xcHl4MVh6UmFSY25tQT09Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K
    rules:
      - operations: [ "CREATE" ]
        apiGroups: [""]
        apiVersions: ["v1"]
        resources: ["pods"]
    admissionReviewVersions: ["v1beta1"]
    sideEffects: None

controlplane ~ ➜  kubectl create -f /root/webhook-configuration.yaml
mutatingwebhookconfiguration.admissionregistration.k8s.io/demo-webhook created

controlplane ~ ➜  


controlplane ~ ➜  cat /root/pod-with-defaults.yaml
# A pod with no securityContext specified.
# Without the webhook, it would run as user root (0). The webhook mutates it
# to run as the non-root user with uid 1234.
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-defaults
  labels:
    app: pod-with-defaults
spec:
  restartPolicy: OnFailure
  containers:
    - name: busybox
      image: busybox
      command: ["sh", "-c", "echo I am running as user $(id -u)"]

controlplane ~ ➜  kubectl apply -f /root/pod-with-defaults.yaml
pod/pod-with-defaults created

controlplane ~ ➜  

controlplane ~ ➜  cat /root/pod-with-override.yaml
# A pod with a securityContext explicitly allowing it to run as root.
# The effect of deploying this with and without the webhook is the same. The
# explicit setting however prevents the webhook from applying more secure
# defaults.
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-override
  labels:
    app: pod-with-override
spec:
  restartPolicy: OnFailure
  securityContext:
    runAsNonRoot: false
  containers:
    - name: busybox
      image: busybox
      command: ["sh", "-c", "echo I am running as user $(id -u)"]

controlplane ~ ➜  kubectl apply -f /root/pod-with-override.yaml
pod/pod-with-override created

controlplane ~ ➜  

controlplane ~ ➜  cat /root/pod-with-conflict.yaml
# A pod with a conflicting securityContext setting: it has to run as a non-root
# user, but we explicitly request a user id of 0 (root).
# Without the webhook, the pod could be created, but would be unable to launch
# due to an unenforceable security context leading to it being stuck in a
# 'CreateContainerConfigError' status. With the webhook, the creation of
# the pod is outright rejected.
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-conflict
  labels:
    app: pod-with-conflict
spec:
  restartPolicy: OnFailure
  securityContext:
    runAsNonRoot: true
    runAsUser: 0
  containers:
    - name: busybox
      image: busybox
      command: ["sh", "-c", "echo I am running as user $(id -u)"]

controlplane ~ ➜  kubectl apply -f /root/pod-with-conflict.yaml
Error from server: error when creating "/root/pod-with-conflict.yaml": admission webhook "webhook-server.webhook-demo.svc" denied the request: runAsNonRoot specified, but runAsUser set to 0 (the root user)

controlplane ~ ✖ 

Mutating webhook should reject the request as its asking to run as root user without setting runAsNonRoot: false
===============================================================================================================================
kubectl explain job
root@controlplane:~# kubectl proxy 8001&
root@controlplane:~# curl localhost:8001/apis/authorization.k8s.io


controlplane ~ ✦ ✖ curl localhost:8001/apis/authorization.k8s.io
{
  "kind": "APIGroup",
  "apiVersion": "v1",
  "name": "authorization.k8s.io",
  "versions": [
    {
      "groupVersion": "authorization.k8s.io/v1",
      "version": "v1"
    }
  ],
  "preferredVersion": {
    "groupVersion": "authorization.k8s.io/v1",
    "version": "v1"
  }
}
controlplane ~ ✦ ➜  

==============================================================================================

cp -v /etc/kubernetes/manifests/kube-apiserver.yaml /root/kube-apiserver.yaml.backup
vim /etc/kubernetes/manifests/kube-apiserver.yaml

controlplane /etc/logrotate.d ✦ ➜  cat /etc/kubernetes/manifests/kube-apiserver.yaml
apiVersion: v1
kind: Pod
metadata:
  annotations:
    kubeadm.kubernetes.io/kube-apiserver.advertise-address.endpoint: 192.44.191.3:6443
  creationTimestamp: null
  labels:
    component: kube-apiserver
    tier: control-plane
  name: kube-apiserver
  namespace: kube-system
spec:
  containers:
  - command:
    - kube-apiserver
    - --advertise-address=192.44.191.3
    - --allow-privileged=true
    - --authorization-mode=Node,RBAC
    - --client-ca-file=/etc/kubernetes/pki/ca.crt
    - --enable-admission-plugins=NodeRestriction
    - --enable-bootstrap-token-auth=true
    - --etcd-cafile=/etc/kubernetes/pki/etcd/ca.crt
    - --etcd-certfile=/etc/kubernetes/pki/apiserver-etcd-client.crt
    - --etcd-keyfile=/etc/kubernetes/pki/apiserver-etcd-client.key
    - --etcd-servers=https://127.0.0.1:2379
    - --kubelet-client-certificate=/etc/kubernetes/pki/apiserver-kubelet-client.crt
    - --kubelet-client-key=/etc/kubernetes/pki/apiserver-kubelet-client.key
    - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
    - --proxy-client-cert-file=/etc/kubernetes/pki/front-proxy-client.crt
    - --proxy-client-key-file=/etc/kubernetes/pki/front-proxy-client.key
    - --requestheader-allowed-names=front-proxy-client
    - --requestheader-client-ca-file=/etc/kubernetes/pki/front-proxy-ca.crt
    - --requestheader-extra-headers-prefix=X-Remote-Extra-
    - --requestheader-group-headers=X-Remote-Group
    - --requestheader-username-headers=X-Remote-User
    - --runtime-config=rbac.authorization.k8s.io/v1alpha1
    - --secure-port=6443
    - --service-account-issuer=https://kubernetes.default.svc.cluster.local
    - --service-account-key-file=/etc/kubernetes/pki/sa.pub
    - --service-account-signing-key-file=/etc/kubernetes/pki/sa.key
    - --service-cluster-ip-range=10.96.0.0/12
    - --tls-cert-file=/etc/kubernetes/pki/apiserver.crt
    - --tls-private-key-file=/etc/kubernetes/pki/apiserver.key
    image: registry.k8s.io/kube-apiserver:v1.27.0
    imagePullPolicy: IfNotPresent
    livenessProbe:
      failureThreshold: 8
      httpGet:
        host: 192.44.191.3
        path: /livez
        port: 6443
        scheme: HTTPS
      initialDelaySeconds: 10
      periodSeconds: 10
      timeoutSeconds: 15
    name: kube-apiserver
    readinessProbe:
      failureThreshold: 3
      httpGet:
        host: 192.44.191.3
        path: /readyz
        port: 6443
        scheme: HTTPS
      periodSeconds: 1
      timeoutSeconds: 15
    resources:
      requests:
        cpu: 250m
    startupProbe:
      failureThreshold: 24
      httpGet:
        host: 192.44.191.3
        path: /livez
        port: 6443
        scheme: HTTPS
      initialDelaySeconds: 10
      periodSeconds: 10
      timeoutSeconds: 15
    volumeMounts:
    - mountPath: /etc/ssl/certs
      name: ca-certs
      readOnly: true
    - mountPath: /etc/ca-certificates
      name: etc-ca-certificates
      readOnly: true
    - mountPath: /etc/kubernetes/pki
      name: k8s-certs
      readOnly: true
    - mountPath: /usr/local/share/ca-certificates
      name: usr-local-share-ca-certificates
      readOnly: true
    - mountPath: /usr/share/ca-certificates
      name: usr-share-ca-certificates
      readOnly: true
  hostNetwork: true
  priority: 2000001000
  priorityClassName: system-node-critical
  securityContext:
    seccompProfile:
      type: RuntimeDefault
  volumes:
  - hostPath:
      path: /etc/ssl/certs
      type: DirectoryOrCreate
    name: ca-certs
  - hostPath:
      path: /etc/ca-certificates
      type: DirectoryOrCreate
    name: etc-ca-certificates
  - hostPath:
      path: /etc/kubernetes/pki
      type: DirectoryOrCreate
    name: k8s-certs
  - hostPath:
      path: /usr/local/share/ca-certificates
      type: DirectoryOrCreate
    name: usr-local-share-ca-certificates
  - hostPath:
      path: /usr/share/ca-certificates
      type: DirectoryOrCreate
    name: usr-share-ca-certificates
status: {}

==============================================================================================

kubectl get po -n kube-system

==============================================================================================

sudo su -
curl -LO https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl-convert
chmod +x kubectl-convert
mv kubectl-convert /usr/local/bin/kubectl-convert

==============================================================================================

Ingress manifest file is already given under the /root/ directory called ingress-old.yaml.

With help of the kubectl convert command, change the deprecated API version to the networking.k8s.io/v1 and create the resource.

kubectl-convert -f ingress-old.yaml --output-version networking.k8s.io/v1 | kubectl apply -f -

controlplane ~ ➜  cat ingress-old.yaml
---
# Deprecated API version
apiVersion: networking.k8s.io/v1beta1
kind: Ingress
metadata:
  name: ingress-space
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
  - http:
      paths:
      - path: /video-service
        pathType: Prefix
        backend:
          serviceName: ingress-svc
          servicePort: 80

controlplane ~ ➜  


===============================================================================================================================
What is a custom resource?
It is an extension of the Kubernetes API that is not necessarily available in a default Kubernetes installation.

============================================================================

crd.yaml
---
apiVersion: apiextensions.k8s.io/v1
kind: CustomResourceDefinition
metadata:
  name: internals.datasets.kodekloud.com 
spec:
  group: datasets.kodekloud.com
  versions:
    - name: v1
      served: true
      storage: true
      schema:
        openAPIV3Schema:
          type: object
          properties:
            spec:
              type: object
              properties:
                internalLoad:
                  type: string
                range:
                  type: integer
                percentage:
                  type: string
  scope: Namespaced 
  names:
    plural: internals
    singular: internal
    kind: Internal
    shortNames:
    - int


controlplane ~ ➜  cat custom.yaml 
---
kind: Internal
apiVersion: datasets.kodekloud.com/v1
metadata:
  name: internal-space
  namespace: default
spec:
  internalLoad: "high"
  range: 80
  percentage: "50"

controlplane ~ ➜  

kubectl create -f custom.yaml
============================================================================
Create a custom resource called datacenter and the apiVersion should be traffic.controller/v1.

Set the dataField length to 2 and access permission should be true.

sample.yaml:
kind: Global
apiVersion: traffic.controller/v1
metadata:
  name: datacenter
spec:
  dataField: 2
  access: true

============================================================================


===============================================================================================================================
kubectl describe svc frontend-service

controlplane ~ ✦ ➜  kubectl describe svc frontend-service 
Name:                     frontend-service
Namespace:                default
Labels:                   app=myapp
Annotations:              <none>
Selector:                 app=frontend
Type:                     NodePort
IP Family Policy:         SingleStack
IP Families:              IPv4
IP:                       10.104.27.129
IPs:                      10.104.27.129
Port:                     <unset>  8080/TCP
TargetPort:               8080/TCP
NodePort:                 <unset>  30080/TCP
Endpoints:                10.244.0.4:8080,10.244.0.5:8080,10.244.0.6:8080 + 2 more...
Session Affinity:         None
External Traffic Policy:  Cluster
Events:                   <none>


kubectl scale deployment --replicas=1 frontend-v2

controlplane ~ ✦ ✖ kubectl scale deployment frontend --replicas=0; kubectl scale deployment frontend-v2 --replicas=5
deployment.apps/frontend scaled
deployment.apps/frontend-v2 scaled

controlplane ~ ✦ ➜  kubectl delete deploy frontend
deployment.apps "frontend" deleted

controlplane ~ ✦ ➜  


===============================================================================================================================
controlplane ~ ➜  uname -a
Linux controlplane 5.4.0-1106-gcp #115~18.04.1-Ubuntu SMP Mon May 22 20:46:39 UTC 2023 x86_64 x86_64 x86_64 GNU/Linux

Install Helm:
curl https://baltocdn.com/helm/signing.asc | apt-key add -
apt-get install apt-transport-https --yes
echo "deb https://baltocdn.com/helm/stable/debian/ all main" | tee /etc/apt/sources.list.d/helm-stable-debian.list
apt-get update
apt-get install helm

controlplane ~ ➜  helm --help | grep client
  env         helm client environment information
  version     print the client version information
      --burst-limit int                 client-side default throttling limit (default 100)

controlplane ~ ➜  helm --help | grep version
  version     print the client version information

controlplane ~ ➜  helm version
version.BuildInfo{Version:"v3.13.1", GitCommit:"3547a4b5bf5edb5478ce352e18858d8a552a4110", GitTreeState:"clean", GoVersion:"go1.20.8"}

controlplane ~ ➜  

controlplane ~ ➜  helm --help | grep verbose
      --debug                           enable verbose output

controlplane ~ ➜ 




===============================================================================================================================

