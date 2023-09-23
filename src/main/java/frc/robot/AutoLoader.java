// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Add your docs here. */
public class AutoLoader {

    /**
     * Enumeration for the possible autos that we'll create
     */
    public enum AutoCommand {
        NONE,                               // Does nothing
        COMMUNITY_EXIT,                     // Just leaves the community without scoring
        BALANCE,                            // Just balance
        SCORE_CUBE_AND_COMMUNITY_EXIT,      // Score cube to level 1 and exit community
        SCORE_CUBE_AND_BALANCE,             // Score cube to level 1 and balance
    }

    private SendableChooser<AutoCommand> m_autoChooser;

    /**
     * Constructor used to initialize the sendable chooser
     */
    public AutoLoader() {
        // Determine which alliance we're in to determine if we need to mirror and
        // invert the trajectories //

        // Initialize the sendable chooser //
        m_autoChooser = new SendableChooser<>();

        // Default option is to always have no auto command running //
        // m_autoChooser.setDefaultOption("None", AutoCommand.NONE);
        m_autoChooser.setDefaultOption("Score Cube and Community Exit", AutoCommand.SCORE_CUBE_AND_COMMUNITY_EXIT);

        // m_autoChooser.addOption("Example Trajectory", AutoCommand.EXAMPLE_TRAJECTORY);
        m_autoChooser.addOption("None", AutoCommand.NONE);
        m_autoChooser.addOption("Community Exit", AutoCommand.COMMUNITY_EXIT);
        m_autoChooser.addOption("Balance", AutoCommand.BALANCE);
        m_autoChooser.addOption("Score Cube and Balance", AutoCommand.SCORE_CUBE_AND_BALANCE);
        m_autoChooser.addOption("Score Cube and Community Exit", AutoCommand.SCORE_CUBE_AND_COMMUNITY_EXIT);

        // Initialize the rest of the options //
        SmartDashboard.putData(m_autoChooser);
    }

    /**
     * 
     * @return The selected command from the m_autoChooser
     */
    public AutoCommand getSelected() {
        return m_autoChooser.getSelected();
    }

}
