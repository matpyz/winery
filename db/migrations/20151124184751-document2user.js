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
    'documents',
    'creatorId',
    {
      type: 'int',
      unsigned: true,
      foreignKey: {
        name: 'documents_creator',
        table: 'user',
        rules: {
          onDelete: 'CASCADE',
          onUpdate: 'RESTRICT'
        },
        mapping: 'id'
      }
    }
  );
  return null;
};

exports.down = function(db) {

  db.removeForeignKey(
    'documents', 
    'documents_creator', 
    {
      dropIndex: true
    },
    function() {
      db.removeColumn(
        'documents',
        'creatorId'
      );
    }
  );
  return null;
};
