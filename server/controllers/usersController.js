// This file defines request handling behaviors for the /users route

const bcrypt = require('bcrypt');
const usersModel = require('../models/usersModel');

// Handle GET /users
module.exports.get = async (req, res) => {
  const results = await usersModel.search();
  res.send(results);
};

// Handle POST /users/signup
module.exports.signup = (req, res) => {
  const user = {
    name: req.body.name.split(' '),
    username: req.body.username,
    email: req.body.email,
    password: req.body.password
  };

  const firstName = user.name[0];
  const lastName = user.name[1];

  // Hash user.password and add the user to the USERS table
  bcrypt.hash(user.password, 10, (err, hash) => {
    try {
      usersModel.add(firstName, lastName, user.username, hash, user.email);
      res.status(201).send('User Added Successfully');
      console.log('User Added Successfully');
    } catch (err) {
      res.status(401).send('Unable to Add User');
    }
  });
};

// Handle POST /users/login
module.exports.authenticate = async (req, res) => {
  const user = {
    email: req.body.email,
    password: req.body.password
  };

  // Get user's hashed password from database
  // Compare given password to hash
  // If passwords match -- Send 200 OK
  // Else -- Send 401 Unauthorized

  try {
    const password = await usersModel.getPassword(user.email);
    bcrypt.compare(user.password, password).then(status => {
      if (status) {
        res.status(200).send('Login Successful');
      } else {
        res.status(401).send('Incorrect Password');
      }
    });
  } catch (error) {
    res.status(401).send('Unauthorized');
  }
};
