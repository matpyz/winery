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
    'invoice',
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
          notNull: true
        },
        receiverName: {
          type: 'string',
          notNull: true
        },
        receiverAddress: {
          type: 'string',
          notNull: true
        },        
        receiverNIP: {
          type: 'string',
          notNull: true
        },        
        receiverDetails: {
          type: 'string',
          notNull: false
        },        
        receiverBankAccount: {
          type: 'string',
          notNull: false
        },        
        creationDate: {
          type: 'datetime',
          notNull: true
        },
        creatorId: {
          type: 'int',
          unsigned: true,
          notNull: true,
          foreignKey: {
            name: 'invoice_creator',
            table: 'user',
            rules: {
              onDelete: 'RESTRICT',
              onUpdate: 'RESTRICT'
            },
            mapping: 'id'
          }
        }
      },
      ifNotExists: true
    }
  );
  
  db.createTable(
    'invoiceDetails',
    {
      columns: {
        id: {
          type: 'int',
          unsigned: true,
          primaryKey: true,
          autoIncrement: true
        },
        invoiceId: {
          type: 'int',
          unsigned: true,
          notNull: true,
          foreignKey: {
            name: 'invoice_detail',
            table: 'invoice',
            rules: {
              onDelete: 'RESTRICT',
              onUpdate: 'RESTRICT'
            },
            mapping: 'id'
          }
        }, 
        name: {
          type: 'string',
          notNull: true
        },
        quantity: {
          type: 'int',
          notNull: true
        },
        baseprice: {
          type: 'int',
          notNull: true
        }
      },
      ifNotExists: true
    }
  );
  
  return null;
};

exports.down = function(db) {
  
  db.dropTable('invoiceDetails');
  db.dropTable('invoice');
  
  return null;
};
