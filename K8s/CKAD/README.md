* https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands#-strong-getting-started-strong-
* https://kodekloud.com/pages/free-labs/kubernetes?ref=hackernoon.com

```
alias k=kubectl
alias kgp='kubectl get pods'
alias kgs='kubectl get svc'
alias kdp='kubectl describe pod'
alias kds='kubectl describe svc'
alias kaf='kubectl apply -f'
alias kcf='kubectl create -f'

alias do="--dry-run=client -o yaml"
alias now="--force --grace-period 0"

# for context/namespace switching
alias kx='kubectl config use-context'
alias kn='kubectl config set-context --current --namespace'
```
