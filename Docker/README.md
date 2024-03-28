```
root@controlplane ~ ✦ ➜  trivy image bitnami/nginx | grep -i critical
Total: 70 (UNKNOWN: 10, LOW: 4, MEDIUM: 33, HIGH: 21, CRITICAL: 2)
| libc-bin          | CVE-2019-1010022 | CRITICAL | 2.36-9+deb12u4        |               | glibc: stack guard protection bypass    |
| libc6             | CVE-2019-1010022 | CRITICAL |                       |               | glibc: stack guard protection bypass    |
```
