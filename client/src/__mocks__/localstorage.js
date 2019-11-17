let mockStorage = {};

const localStorage = {
  setItem: (key, val) => Object.assign(mockStorage, {
    [key]: val
  }),
  getItem: (key) => mockStorage[key],
  clear: () => mockStorage = {}
};

export default localStorage;