class CreateReportImages < ActiveRecord::Migration
  def change
    create_table :report_images do |t|
      t.string :image
      t.timestamps null: false
    end

    add_reference :report_images, :report, index: true, foreign_key: true
  end
end
