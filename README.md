# url-shrinker

An url shrinker app using Java with Spring boot with some deep computer engineer concepts applied

## Functional Requirements

**1 - URL Shrinker:** from a long URL generate a short URL and return it.

**2 - URL redirect:** redirect the shortened url to original url

**3 - Endpoints**: two endpoints it is enought to solve this problem

Request

**(POST: /api/v1/shorten)** 

**body**: {"url": "https://example.com...."}

Response

**(GET: "bit.ly/z941dadj")**

Status code: 301/302

Header
  Location: "https://example.com"

and another to redirect **(GET)**

## Non-functional requiriments

**1** - The system has to support 100 millions of generated url by day.

**2** - The url size shortened should be the little size possible .

**3** - Only number (0-9) and chars (az, AZ) are permited.

**4** - For each 1 write operation in database, there will be 10 of read.

**5** - The medium size of shortened url will be 100 bytes.

**6** - The URL`s should be persisted in a 10 years minimum period.

**7** - The system should operate in high disponibility 24/7

