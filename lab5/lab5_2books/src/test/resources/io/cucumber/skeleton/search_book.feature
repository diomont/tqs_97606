Feature: Search for books
  Background: Available books
    Given a set of books initialized by the following table
      | title             | author             | published    | categories                   |
      | Parry Hotter      | Jason Keys         | 2010-02-12   | fantasy, adventure           |
      | The Hunger Games  | Suzanne Collins    | 2008-09-14   | sci-fi, adventure            |
      | Catching Fire     | Suzanne Collins    | 2009-09-01   | sci-fi, adventure            |
      | Mocking Jay       | Suzanne Collins    | 2010-08-24   | sci-fi, adventure            |
      | 1984              | George Orwell      | 1949-06-08   | dystopian, political fiction |
      | Animal Farm       | George Orwell      | 1945-08-17   | political satire             |
      | Starship Troopers | Robert A. Heinlein | 1959-11-05   | sci-fi, political fiction    |

  Scenario Outline: Search by author
    When a customer searches for books written by <author>
    Then <total_a> books should be found
      And the book titles should be <titles_a>
  Examples: Author search results
    | author            | total_a | titles_a                                       |
    | "Suzanne Collins" | 3       | "The Hunger Games, Catching Fire, Mocking Jay" |
    | "George Orwell"   | 2       | "Animal Farm, 1984"                            |
    | "Jason Keys"      | 1       | "Parry Hotter"                                 |

  Scenario Outline: Search by category
    When a customer searches for books with the category <category>
    Then <total_b> books should be found
      And the book titles should be <titles_b>
  Examples: Category search results
    | category            | total_b | titles_b                                                          |
    | "adventure"         | 4       | "The Hunger Games, Catching Fire, Mocking Jay, Parry Hotter"      |
    | "sci-fi"            | 4       | "The Hunger Games, Catching Fire, Mocking Jay, Starship Troopers" |
    | "political fiction" | 2       | "1984, Starship Troopers"                                         |

  Scenario Outline: Search by year
    When a customer searches for books published between <year_start> and <year_end>
    Then <total_c> books should be found
      And the book titles should be <titles_c>
  Examples: Year search results
    | year_start | year_end | total_c | titles_c                                   |
    | 2009       | 2011     | 3       | "Catching Fire, Mocking Jay, Parry Hotter" |
    | 1940       | 1960     | 3       | "Animal Farm, 1984, Starship Troopers"     |
    | 1940       | 1950     | 2       | "Animal Farm, 1984"                        |

