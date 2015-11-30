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
    'eventType',
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
        }
      },
      ifNotExists: true
    }
  );

  db.createTable(
    'event',
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
        description: 'string',
        startDate: {
          type: 'datetime',
          notNull: true
        },
        endDate: 'datetime',
        location: 'string',
        creatorId: {
          type: 'int',
          unsigned: true,
          notNull: true,
          foreignKey: {
            name: 'event_creator',
            table: 'user',
            rules: {
              onDelete: 'CASCADE',
              onUpdate: 'RESTRICT'
            },
            mapping: 'id'
          }
        },
        eventTypeId: {
          type: 'int',
          unsigned: true,
          notNull: true,
          foreignKey: {
            name: 'event_event_type',
            table: 'eventType',
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
  
  db.createTable(
    'user2event',
    {
      columns: {
        userId: {
          type: 'int',
          unsigned: true,
          notNull: true,
          foreignKey: {
            name: 'user2event_user',
            table: 'user',
            rules: {
              onDelete: 'CASCADE',
              onUpdate: 'CASCADE'
            },
            mapping: 'id'
          }
        },
        eventId: {
          type: 'int',
          unsigned: true,
          notNull: true,
          foreignKey: {
            name: 'user2event_event',
            table: 'event',
            rules: {
              onDelete: 'CASCADE',
              onUpdate: 'CASCADE'
            },
            mapping: 'id'
          }
        },
        access: {
          type: 'int',
          unsigned: true,
          notNull: true
        }
      },
      ifNotExists: true
    }
  );

  db.createTable(
    'group2event',
    {
      columns: {
        groupId: {
          type: 'int',
          unsigned: true,
          notNull: true,
          foreignKey: {
            name: 'group2event_group',
            table: 'group',
            rules: {
              onDelete: 'CASCADE',
              onUpdate: 'CASCADE'
            },
            mapping: 'id'
          }
        },
        eventId: {
          type: 'int',
          unsigned: true,
          notNull: true,
          foreignKey: {
            name: 'group2event_event',
            table: 'event',
            rules: {
              onDelete: 'CASCADE',
              onUpdate: 'CASCADE'
            },
            mapping: 'id'
          }
        },
        access: {
          type: 'int',
          unsigned: true,
          notNull: true
        }
      },
      ifNotExists: true
    }
  );
  
  return null;
};

exports.down = function(db) {
  
  db.dropTable('user2event');
  db.dropTable('group2event');
  db.dropTable('event');
  db.dropTable('eventType');
  
  
  return null;
};
