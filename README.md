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

## Insights

### Generating the shortcode (The size)

The url generated has to be only numbers (0-9) and chars [az AZ].

10 numeric digits: 0-9<br/>
26 Lower char: a - z<br/>
26 Upper char: A - Z

**Total: 10 + 26 + 26 = 62 chars**

    0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz

Finding the max and shorter char length of the shortcode.

| Char quantity | Max number of URLs                        |
|---------------|-------------------------------------------|
| 1             | 62^1 = 62                                 |
| 2             | 62^2 = 3.844                              |
| 3             | 62^3 = 238.328                            |
| 4             | 62^4 = 14.776.336                         |
| 5             | 62^5 = 916.132.832                        |
| 6             | 62^1 = 56.800.235.584                     |
| 7             | 62^1 = 3.521.614.606.208 = ~3,5 trillions |

The maximum char length could be **7**, because we must be capable to store these quantity of url, based on Non-functional requirements:

    100.000.000 * 365 * 10 = 365 billions of urls

100.000.000 Millions by day is equal= 100.000.000 / 24 / 60 / 60 = 1160 write per second

Read operations 10:1 = **1160 * 10** = **11.600**  

It is heavy operations for the database, so we have to consider use a cache system maybe to get the original URL by shortcode.

We have to optimize the database usage, so its not a good idea generate random Hashcode to URL and beign search in the database if the id already exists to avoid duplicate keys.
Its possible that the system stay in a big loop if already have a lot of URLs generated, trying to check if the generated shortcode already exists.

### Generating the shortcode (The method)

- Random Generate

We would immediatily start thinking in use a HashCode base 62 generator based. 
But with these random methods we could have a colision of generated id.

To eliminate the colision possibility and the necessity of use database, we have to generate a unique shortecode using **base conversion**.

So we have to sequencial generate a decimal number and convert him to equivalent base62 String. In that way we dont have colisions.

**Representation Example**

| Decimal | base62  |
|---------|---------|
| 0       | 0       |
| ......  | ......  |       
| 15      | F       |
| ....... | ....... |
| 61      | z       |

    sas