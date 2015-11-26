'use strict';

var dbm;
var type;
var seed;

/**
  * We receive the dbmigrate dependency from dbmigrate initially.
  * This enables us to not have to rely on NODE_PATH.
  */
exports.setup = function(options, seedLink) {
  dbm = options.dbmigrate;
  type = dbm.dataType;
  seed = seedLink;
};

exports.up = function(db) {
  db.createTable('documents', {
    columns: {
      id: {
        type: 'int',
        primaryKey: true,
        autoIncrement: true
      },
      name: 'string',
      description: 'string',
      type: 'string',
      createdAt: 'datetime',
      document: 'blob'
    },
    ifNotExists: true
  });
  return null; 
};

exports.down = function(db) {
  db.dropTable('documents');
  return null;
};
