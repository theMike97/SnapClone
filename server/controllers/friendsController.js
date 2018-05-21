// This file defines request handling behaviors for the /friends route

const friendsModel = require('../models/friendsModel');

// Handle GET /friends
module.exports.get = async (req, res) => {
  const results = await friendsModel.search();
  res.send(results);
};
