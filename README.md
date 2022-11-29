Set Env:

```bash
export SERVER_PORT=8080
export STARGATE_USER=$(oc -n k8ssandra-operator get secret k8ssandra-cluster-superuser -o jsonpath="{.data.username}" | base64 -d)
export STARGATE_PW=$(oc -n k8ssandra-operator get secret k8ssandra-cluster-superuser -o jsonpath="{.data.password}" | base64 -d)
export STARGATE_AUTH_URL=https://$(oc -n k8ssandra-operator get route sg-auth -o jsonpath="{.spec.host}")
export STARGATE_JSON_URL=https://$(oc -n k8ssandra-operator get route sg-rest -o jsonpath="{.spec.host}")
```

```bash
curl localhost:8080/book-info/book-by-isbn/0061031321 | jq
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

GET https://sg-rest-k8ssandra-operator.apps-crc.testing/v2/namespaces/home_library/collections/book_catalog?where=%7B%2522identifiers.isbn10List.%5B*%5D.isbn10%2522%3A%7B%2522%24eq%2522%3A%25220061031321%2522%7D%7D, Status[400 Bad Request], Headers[date=Mon, 28 Nov 2022 19:38:50 GMT content-type=application/json set-cookie=af3ca6a7014ba62db41735906a1d9f76=e4529690fa21c1b9c40c1bb3a3c7453a; path=/; HttpOnly; Secure; SameSite=None content-length=108], Body:
{"description":"The `where` parameter expects a valid JSON object representing search criteria.","code":400}

GET https://sg-rest-k8ssandra-operator.apps-crc.testing/v2/namespaces/home_library/collections/book_catalog?where=%7B%22identifiers.isbn10List.%5B*%5D.isbn10%22%3A%7B%22%24eq%22%3A%220061031321%22%7D%7D Headers[Accept=application/json User-Agent=Resteasy Reactive Client X-Cassandra-Token=391c3c86-eb34-46dd-8ac6-13074b399415], Empty body

{"data":{"OL24385514M":{"authors":[{"name":"Terry Pratchett","openLibraryUrl":"http://openlibrary.org/authors/OL25712A/Terry_Pratchett"}],"catalogId":"OL24385514M","coverImageUrl":"https://covers.openlibrary.org/b/id/6636627-S.jpg","identifiers":{"isbn10List":[{"isbn10":"0061031321"}],"isbn13List":[{"isbn13":"9780061031328"}]},"inCatalog":false,"numberOfPages":357,"openLibraryUrl":"http://openlibrary.org/books/OL24385514M/Thief_of_time","publishDate":"2002","title":"Thief of time"}}}

```json
{
  "data": {
    "OL24385514M": {
      "authors": [
        {
          "name": "Terry Pratchett",
          "openLibraryUrl": "http://openlibrary.org/authors/OL25712A/Terry_Pratchett"
        }
      ],
      "catalogId": "OL24385514M",
      "coverImageUrl": "https://covers.openlibrary.org/b/id/6636627-S.jpg",
      "identifiers": {
        "isbn10List": [
          {
            "isbn10": "0061031321"
          }
        ],
        "isbn13List": [
          {
            "isbn13": "9780061031328"
          }
        ]
      },
      "inCatalog": false,
      "numberOfPages": 357,
      "openLibraryUrl": "http://openlibrary.org/books/OL24385514M/Thief_of_time",
      "publishDate": "2002",
      "title": "Thief of time"
    }
  }
}
```

{"OL24385514M":{"authors":[{"name":"Terry Pratchett","openLibraryUrl":"http://openlibrary.org/authors/OL25712A/Terry_Pratchett"}],"catalogId":"OL24385514M","coverImageUrl":"https://covers.openlibrary.org/b/id/6636627-S.jpg","identifiers":{"isbn10List":[{"isbn10":"0061031321"}],"isbn13List":[{"isbn13":"9780061031328"}]},"inCatalog":false,"numberOfPages":357,"openLibraryUrl":"http://openlibrary.org/books/OL24385514M/Thief_of_time","publishDate":"2002","title":"Thief of time"}}