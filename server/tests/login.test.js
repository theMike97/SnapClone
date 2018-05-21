const usersModel = require('../models/usersModel');

// TODO: Improve
test('Checks Authentication', () => {
  const result = usersModel.getPassword('doesntexist@gmail.com');
  console.log(result);
  expect(result).not.toBeNull();
});
