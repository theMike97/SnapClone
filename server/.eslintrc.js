module.exports = {
  extends: 'airbnb-base',
  rules: {
    'comma-dangle': ['error', 'only-multiline'],
    'no-console': 'off',
    'arrow-parens': 'off',
    'no-shadow': ['error', { allow: ['err'] }],
    'no-param-reassign': 'off',
    'import/newline-after-import': 'off'
  },
  env: {
    jest: true
  }
};
