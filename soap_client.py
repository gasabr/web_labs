from zeep import Client

client = Client('http://localhost:8080/ws/')
result = client.service.GetAlbums()
print(result)