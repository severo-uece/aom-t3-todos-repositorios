// import necessary module
import { check } from 'k6';
import http from 'k6/http';

export const options = {
  thresholds: {
    http_req_failed: ['rate<0.01'], // http errors should be less than 1%
    http_req_duration: ['p(99)<1000'], // 99% of requests should be below 1s
  },
  scenarios: {
    // arbitrary name of scenario
    average_load: {
      executor: 'ramping-vus',
      stages: [
        // ramp up to average load of 20 virtual users
        { duration: '50s', target: 20 },
        // maintain load
        { duration: '10s', target: 20 },
        // ramp down to zero
        { duration: '5s', target: 0 },
      ],
    },
  }
};

var ID = 1;

  export default function() {
    // define URL and payload
    const url = 'http://localhost:8090/customers-ms/customers';
    const payload = JSON.stringify({
      name: `Cliente de Teste K6 ${ID++}`,
      age: 30,
      email: "cliente_k6@empresa.com"
    });

    const params = {
      headers: {
        'Content-Type': 'application/json',
      },
    };

    const res = http.post(url, payload, params);

    check(res, {
      'Response code foi 200': (res) => res.status == 200,
    });
  }