Set Env:

```bash
export SERVER_PORT=8080
export STARGATE_USER=$(oc -n k8ssandra-operator get secret k8ssandra-cluster-superuser -o jsonpath="{.data.username}" | base64 -d)
export STARGATE_PW=$(oc -n k8ssandra-operator get secret k8ssandra-cluster-superuser -o jsonpath="{.data.password}" | base64 -d)
export STARGATE_AUTH_URL=https://$(oc -n k8ssandra-operator get route sg-auth -o jsonpath="{.spec.host}")
```

Create KeyStore:

```bash
mkdir ~/cert-work-dir
openssl s_client -showcerts -servername $(oc -n k8ssandra-operator get route sg-auth -o jsonpath="{.spec.host}") -connect $(oc -n k8ssandra-operator get route sg-auth -o jsonpath="{.spec.host}"):443 </dev/null 2>/dev/null|openssl x509 -outform PEM > ~/cert-work-dir/crc.crt
keytool -noprompt -importcert -file ~/cert-work-dir/crc.crt -keystore ~/cert-work-dir/keystore.jks -deststoretype pkcs12 -keypass changeit -storepass changeit
```

```bash
quarkus dev -D=javax.net.ssl.trustStore=~/cert-work-dir/keystore.jks -D=javax.net.ssl.trustStorePassword=changeit
```
