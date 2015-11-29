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
    'user',
    'login',
    {
      type: 'string',
      unique: true,
      notNull: true
    }
  );
  db.addColumn(
    'user',
    'surname', 
    {
      type: 'string'  
    }
  );
  db.changeColumn(
    'user',
    'email',
    {
      type: 'string',
      unique: true,
      notNull: true
    }
  );
  db.changeColumn(
    'user',
    'password',
    {
      type: 'string',
      notNull: true
    }
  );
  db.changeColumn(
    'group',
    'name',
    {
      type: 'string',
      unique: true,
      notNull: true
    }
  );
  db.changeColumn(
    'group2permission',
    'access',
    {
      type: 'int',
      notNull: true    
    }
  );
  db.changeColumn(
    'permission',
    'name',
    {
      type: 'string',
      unique: true,
      notNull: true
    }
  );
  
  return null;
};

exports.down = function(db) {
  db.removeColumn(
    'user',
    'login'
  );
  db.removeColumn(
    'user',
    'surname'
  );
  db.changeColumn(
    'user',
    'email',
    {
      type: 'string',
      unique: false,
      notNull: false
    }
  );
  db.changeColumn(
    'user',
    'password',
    {
      type: 'string',
      notNull: false
    }
  );
  db.changeColumn(
    'group',
    'name',
    {
      type: 'string'
    }
  );
  db.changeColumn(
    'group2permission',
    'access',
    {
      type: 'int'
    }
  );
  db.changeColumn(
    'permission',
    'name',
    {
      type: 'string'
    }
  );
  return null;
};
