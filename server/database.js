// This file acts as database middleware
// and is used in conjunction with models

const mysql = require('mysql');
const util = require('util');
const config = require('./config.json');

const pool = mysql.createPool({
  host: `${config.host}`,
  user: `${config.user}`,
  password: `${config.password}`,
  database: `${config.schema}`
});

pool.getConnection((err, connection) => {
  if (err) {
    console.error(`Error Connecting! ${err.stack}`);
    if (connection) connection.release();
  }
});

// Promisify query method, so async / await syntax can be used
pool.query = util.promisify(pool.query);

module.exports = pool;
