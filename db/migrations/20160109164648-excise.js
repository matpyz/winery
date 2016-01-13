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
  
  db.createTable(
    'excise',
    {
      columns: {
        id: {
          type: 'int',
          unsigned: true,
          primaryKey: true, 
          autoIncrement: true 
        },
        name: {
          type: 'string',
          unique: true,
          notNull: true
        },        
        amount: 'int'
      },
      ifNotExists: true
    }
  );
  
  return null;
};

exports.down = function(db) {
  
  db.dropTable('excise');
  
  return null;
};
