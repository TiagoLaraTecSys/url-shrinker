import http from 'k6/http';
import { check, sleep } from 'k6';

//export const options = {
//  vus: 50,            // usuários simultâneos
//  duration: '30s',    // duração
//};
//
//export const options = {
//  stages: [
//    { duration: '30s', target: 20 },
//    { duration: '10s', target: 500 }, // pico absurdo
//    { duration: '30s', target: 50 },
//    { duration: '30s', target: 0 },
//  ],
//};

//Saturacao progressiva
export const options = {
  stages: [
    { duration: '2m', target: 50 },    // baseline
    { duration: '2m', target: 100 },   // leve
    { duration: '2m', target: 200 },   // moderado
    { duration: '2m', target: 400 },   // pesado
    { duration: '2m', target: 800 },   // stress alto
    { duration: '2m', target: 1200 },  // saturação real
    { duration: '2m', target: 0 },     // recovery
  ],
};

export default function () {

  // 1. criar URL curta
  const payload = JSON.stringify({
    url: 'https://google.com/' + Math.random()
  });

  const headers = {
    'Content-Type': 'application/json',
  };

  const createRes = http.post(
    'http://localhost:8080/api/v1/shorter',
    payload,
    { headers }
  );

  check(createRes, {
    'create status 200/201': (r) => r.status === 200 || r.status === 201,
  });

  const body = createRes.json();

  if (!body || !body.shortUrl) {
    return;
  }

  // 2. acessar redirect
  const redirectRes = http.get(body.shortUrl, {
    redirects: 0, // importante: não seguir redirect
  });

  check(redirectRes, {
    'redirect status 301/302': (r) => r.status === 301 || r.status === 302,
  });

  sleep(1);
}