// This file contains info about different /friends routes

const express = require('express');
const friendsController = require('../controllers/friendsController');
const router = express.Router();

// GET /friends
router.get('/', friendsController.get);

module.exports = router;
