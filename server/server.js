const bodyParser = require('body-parser');
const express = require('express');
const users = require('./routes/usersRoutes');
const friends = require('./routes/friendsRoutes');

const port = 3000;
const app = express();

// Support parsing of application/json post data
app.use(bodyParser.json());

// Support parsing of application/x-www-from-urlencoded post data
app.use(bodyParser.urlencoded({ extended: true }));

// Define routes
app.use('/users', users);
app.use('/friends', friends);

// Define behavior for base path
app.get('/', (req, res) => {
  res.send('Hello! Welcome to the API!');
});

app.listen(port, () => {
  console.log(`Server started on port ${port}`);
});

module.exports = app;
