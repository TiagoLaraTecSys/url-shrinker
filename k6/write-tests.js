import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 20,            // usuários simultâneos
  duration: '30s',    // duração
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

  sleep(1);
}