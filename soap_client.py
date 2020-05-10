from zeep import Client

client = Client('http://localhost:8080/ws')
result = client.service.GetAlbums()
print(result)

import requests

with open('file.xsd', 'r') as f:
    data = f.read()
r = requests.post('https://www.liquid-technologies.com/api/Converter',
                  json={"Filename": "schema.xsd", "Type": "xsd", "Data": data, "TargetType": "xml",
                        "Arguments": {"elementName": "Root", "elementNamespace": "", "addAnyAttributes": False,
                                      "addAnyElements": False, "forceOptionItemsToDepthOf": "4",
                                      "forceXsiNamespaceDelaration": False, "maxDepthToCreateOptionalItems": "7",
                                      "indent": "2", "indentChar": " ", "indentAttributes": False, "seed": "9722"}})
with open('file.xml', 'w') as f:
    f.write(r.json()['Files'][0]['Data'])
