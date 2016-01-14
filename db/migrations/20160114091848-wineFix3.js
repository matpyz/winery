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
  
  db.addColumn(
    'wine',
    'forSale',
    {
      type: 'int'
    }
  );
    
  db.addColumn(
    'seed',
    'protectedOrigin',
    {
      type: 'int'
    }
  ); 

  db.addColumn(
    'seed',
    'additional2',
    {
      type: 'string'
    }
  ); 

  db.addColumn(
    'seed',
    'year',
    {
      type: 'int'
    }
  ); 
  
  return null;
};

exports.down = function(db) {
  
  db.removeColumn(
    'wine',
    'forSale'
  );
  db.removeColumn(
    'seed',
    'protectedOrigin'
  );
  db.removeColumn(
    'seed',
    'additional2'
  );  
  db.removeColumn(
    'seed',
    'year'
  );  
  return null;
};
