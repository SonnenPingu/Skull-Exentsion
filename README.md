Geyser Custom Skulls Extension

The Geyser Custom Skulls Extension allows you to register and manage custom skulls on your Minecraft server. This extension uses SQLite to store and load skull data and integrates seamlessly with GeyserMC to make the skulls available in your Bedrock Edition.
Features

    Custom Skull Registration: Add and register custom skulls for use in the game.
    SQLite Database Integration: Manage skull data in an SQLite database.
    Automatic Initialization: The extension registers custom skulls when the server starts.

Installation

    Download the Extension:
        Download the latest version of the extension from Releases Tab.

    Place the Extension:
        Copy the downloaded .jar file into the extensions folder of your Geyser server.

    Add the SQLite JAR:
        Ensure that you place the SQLite JAR in the extensions/lib folder, as the extension requires it. The SQLite JAR file is needed to establish database connections.

    Restart the Server:
        Restart your Geyser server to activate the extension. The extension will automatically create the necessary files and directories.

Configuration

    Create the Database:
        The extension expects an SQLite database file in the Skull-Expand/skulldb/ directory. Create an SQLite database named skulls.db and ensure it contains the skulls_skull table. The table should have a texture_url column for the skull URLs or hashes.

    Adjust Database Path:
        The database path is defined in the extension configuration. Ensure that the path to the SQLite database is correct. The extension uses the following default path:


    private static final String DATABASE_URL = "jdbc:sqlite:/home/container/extensions/Skull-Expand/skulls.db";

Usage

    Add Skulls to the Database:
        Add new skulls by updating the custom_skulls table in your SQLite database. Here's an example SQL query to add a new skull:


    INSERT INTO custom_skulls (texture_url) VALUES ('http://textures.minecraft.net/texture/abcd1234');

    Register Skulls:
        Skulls will be automatically registered when the server starts and the extension initializes. The extension uses the GeyserDefineCustomSkullsEvent to register the skulls.

    In-Game Usage:
        After registration, you can use the custom skulls in-game by referencing their hash or URL.

Troubleshooting

    No Skulls Registered:
        Check the log files for errors related to the extension loading or database access.

    Database Connection Issues:
        Verify that the database path is correct and that the server has the necessary permissions to access the file.

Contributing

If you wish to contribute to the improvement of the extension, please follow these steps:

    Fork the Repository.
    Create a Branch for Your Changes.
    Make Your Changes.
    Submit a Pull Request.

License

This extension is licensed under the MIT License. See the LICENSE file for more information.
Contact

For questions or issues, please contact us through the GeyserMC community or open an issue on GitHub.
