package io.github.venkat1701.yugantaarbackend.models.sponsors;

import jakarta.persistence.*;

/**
 * Represents a sponsor in the application.
 * <p>
 * The Sponsor class serves to define a sponsor entity that can be associated with various events
 * and provide sponsorship support. It includes details about the sponsor such as their name, logo,
 * website, description, and the level of sponsorship they provide.
 * </p>
 *
 * <p>
 * Author: Venkat
 * </p>
 */
@Entity
@Table(name = "sponsors")
public class Sponsor {

    /**
     * Unique identifier for the sponsor.
     * <p>
     * This field is automatically generated and serves as the primary key for the Sponsor entity.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Name of the sponsor.
     * <p>
     * This field holds the name of the sponsor and is essential for identification purposes.
     * </p>
     */
    private String name;

    /**
     * URL of the sponsor's logo.
     * <p>
     * This field contains the link to the sponsor's logo, which can be used for branding
     * and promotional materials.
     * </p>
     */
    private String logoUrl;

    /**
     * Website URL of the sponsor.
     * <p>
     * This field holds the link to the sponsor's official website, enabling users to find more
     * information about the sponsor's offerings and background.
     * </p>
     */
    private String websiteUrl;

    /**
     * Description of the sponsor.
     * <p>
     * This field provides a brief overview of the sponsor, outlining their mission, values,
     * and the nature of their business.
     * </p>
     */
    private String description;

    /**
     * Level of sponsorship provided by the sponsor.
     * <p>
     * This field indicates the tier or category of sponsorship (e.g., Gold, Silver, Bronze),
     * which helps to distinguish between different levels of financial support.
     * </p>
     */
    private String sponsorshipLevel;

    /**
     * Timestamp indicating when the sponsor was created.
     * <p>
     * This field should ideally be annotated with a timestamping mechanism to automatically capture
     * the creation time of the sponsor record.
     * </p>
     */
    private String createdAt;

    /**
     * Timestamp indicating when the sponsor was last updated.
     * <p>
     * This field should also be annotated with a timestamping mechanism to automatically capture
     * the last updated time of the sponsor record.
     * </p>
     */
    private String updatedAt;

}
