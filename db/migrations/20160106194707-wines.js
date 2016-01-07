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
    'wine',
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
        grapes: 'string',
        color: 'string',
        produced: {
          type: 'int',
          notNull: true
        },
        sold: {
          type: 'int',
          notNull: true
        },
        baseprice: {
          type: 'int',
          notNull: true
        },
        productionCost: {
          type: 'int',
          notNull: true
        }
      },
      ifNotExists: true
    }
  );
  
  db.createTable(
    'seed',
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
        qty: 'int',
        additional: 'string'
      },
      ifNotExists: true
    }
  );
  
  return null;
};

exports.down = function(db) {
  
  db.dropTable('wine');
  db.dropTable('seed');
  
  return null;
};
