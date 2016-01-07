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
    'fieldStatus',
    {
      columns: {
        id: { 
          type: 'int', 
          unsigned: true,
          primaryKey: true, 
          autoIncrement: true 
        },
        code: {
          type: 'string',
          unique: true,
          notNull: true
        },
        category: {
          type: 'string',
          notNull: true
        },
        subcategory: {
          type: 'string',
          notNull: true
        }
      },
      ifNotExists: true
    }
  );
  
  db.createTable(
    'fieldCell',
    {
      columns: {
        id: {
          type: 'int', 
          unsigned: true,
          primaryKey: true, 
          autoIncrement: true 
        },
        row: {
          type: 'int',
          notNull: true
        },
        col: {
          type: 'int',
          notNull: true
        },
        section: {
          type: 'string',
          notNull: true
        },
        currentStatusId: {
          type: 'int',
          unsigned: true,
          notNull: true,
          foreignKey: {
            name: 'field_current',
            table: 'fieldStatus',
            rules: {
              onDelete: 'RESTRICT',
              onUpdate: 'CASCADE'
            },
            mapping: 'id'
          }
        }
      },
      ifNotExists: true
    }
  );
  
  return null;
};

exports.down = function(db) {
  
  db.dropTable('fieldCell');
  db.dropTable('fieldStatus');
  
  return null;
};
