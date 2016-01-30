import requests
import oauth2 as oauth
from requests.auth import HTTPBasicAuth
from urllib.parse import parse_qsl
from goodreads import Good
from urllib.parse import urlencode
from bs4 import BeautifulSoup

API_KEY = 'OjyBECynlWliftzJdwmqA'
API_SCRT_KEY = 'F1YLHobIuTKGNEyTvwxwaYv2QW7252T2KXd6IWOnWE'

OAUTH_KEY = 'Y2yWPj3IZivAbQ7CAcXi0A'
OAUTH_SCRT_KEY = 's5QOq80VQ8oIPtZdPcfephehEuGAGPGvHr7lwBiCTE'

token = oauth.Token(OAUTH_KEY,OAUTH_SCRT_KEY)
consumer = oauth.Consumer(key=API_KEY,
                              secret=API_SCRT_KEY)
client = oauth.Client(consumer,token)

url = 'http://www.goodreads.com'
client = oauth.Client(consumer, token)
# the book is: "Generation A" by Douglas Coupland
headers = {'content-type': 'application/x-www-form-urlencoded'}
response, content = client.request('%s/review/list/30067343?v=2&shelf=to-read&key=OjyBECynlWliftzJdwmqA&format=xml' % url,
                                   'GET')
# check that the new resource has been created
if response['status'] != '201':
    raise Exception('Cannot create resource: %s' % response['status'])
else:
    print('Book added!')
print()
def getAccess():
    url = 'http://www.goodreads.com'
    request_token_url = '%s/oauth/request_token' % url
    authorize_url = '%s/oauth/authorize' % url
    access_token_url = '%s/oauth/access_token' % url



    client = oauth.Client(consumer)

    response, content = client.request(request_token_url, 'GET')
    if response['status'] != '200':
        raise Exception('Invalid response: %s' % response['status'])

    request_token = dict(parse_qsl(content))

    authorize_link = '%s?oauth_token=%s' % (authorize_url,
                                            request_token.get(b'oauth_token'))
    print(authorize_link)
    accepted = 'n'
    while accepted.lower() == 'n':
        # you need to access the authorize_link via a browser,
        # and proceed to manually authorize the consumer
        accepted = input('Have you authorized me? (y/n) ')

    token = oauth.Token(request_token['oauth_token'],
                        request_token['oauth_token_secret'])

    client = oauth.Client(consumer, token)
    response, content = client.request(access_token_url, 'POST')
    if response['status'] != '200':
        raise Exception('Invalid response: %s' % response['status'])

    access_token = dict(parse_qsl(content))

    # this is the token you should save for future uses
    token = oauth.Token(access_token['oauth_token'],
                        access_token['oauth_token_secret'])
    return 0;






